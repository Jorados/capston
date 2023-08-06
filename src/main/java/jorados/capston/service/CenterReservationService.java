package jorados.capston.service;


import jorados.capston.domain.Center;
import jorados.capston.domain.CenterReservation;
import jorados.capston.domain.User;
import jorados.capston.domain.type.CenterReservationStatus;
import jorados.capston.domain.type.ReservingTime;
import jorados.capston.dto.response.CenterInfoResponseDto;
import jorados.capston.exception.*;
import jorados.capston.repository.CenterRepository;
import jorados.capston.repository.CenterReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jorados.capston.dto.CenterReservationDto.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CenterReservationService {
    private final CenterReservationRepository centerReservationRepository;
    private final CenterRepository centerRepository;
    private final UserService userService;

    // 예약하기
    @Transactional
    public CreateReservationResponse createReservation(User user, Long centerId, CreateReservationRequest request) {

        // 1. 센터 찾기
        Center findCenter = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        // 2. 에러처리 ( 겹치는 예약 )
        if (isAlreadyReservedTimes(findCenter, request.getReservingDate() , request.getReservingTimes())) {
            throw new AlreadyReservedTime();
        }

        // 3. 예약 생성
        int reservationPrice = getPrice(centerId, request.getReservingDate(), request).getPrice();
        CenterReservation reservation = CenterReservation.fromRequest(findCenter, user, request, reservationPrice);

        // 3-2 에러처리 ( 포인트가 부족한지 아닌지 )
        if(!pointComparison(user,reservation)){
            throw new PointLack();
        }

        // 4. 저장
        userService.PointUpdate(user.getId(),reservationPrice);
        centerReservationRepository.save(reservation);

        // 5. 리턴 Response 작성
        return CreateReservationResponse.builder()
                .reservationId(reservation.getId())
                .openTime(findCenter.getOpenTime().getTime())
                .closeTime(findCenter.getCloseTime().getTime())
                .centerId(findCenter.getId())
                .centerName(findCenter.getCenter_name())
                .reservedTimes(reservation.getReservingTimes().stream()
                        .map(ReservingTime::getTime)
                        .collect(Collectors.toList()))
                .pricePerHalfHour(reservation.getPrice())
                .headCount(reservation.getHeadCount())
                .date(reservation.getReservingDate().toString())
                .build();
    }

    // 예약삭제
    public void deleteReservation(User user, Long centerId, Long reservationId) {
        CenterReservation reservation = centerReservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFound());

        Center center = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        if (!reservation.getCenter().getId().equals(centerId)) {
            throw new CenterReservationNotMatch();
        }

        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new UnAuthorizedAccess();
        }

        if (reservation.getStatus() != CenterReservationStatus.RESERVED) {
            throw new CouldNotCancelReservation();
        }

        userService.CancelPoint(user.getId(),reservation.getReservingTimes().size());
        centerReservationRepository.delete(reservation);
    }

    // 내 예약목록
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getAllReservationsByUser(User user, Pageable pageable) {
        return centerReservationRepository
                .findAllByUserOrderByReservingDateDesc(user, pageable)
                .map(ReservationResponse::fromEntity);
    }

    // 특정 체육관 [ 예약 페이지 정보 ] 불러오기
    @Transactional(readOnly = true)
    public ReservationCenterInfoResponse getStadiumReservationInfo(Long centerId, LocalDate date) {
        Center center = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        // 해당 체육관의 해당 날짜에 이미 예약된 시간들
        List<String> reservedTimes = new ArrayList<>();
        centerReservationRepository
                .findAllByCenterAndReservingDate(center, date)
                .forEach(reservation -> {
                    reservation.getReservingTimes().forEach(
                            reservingTime -> {
                                reservedTimes.add(reservingTime.getTime());
                            }
                    );
                });

        return ReservationCenterInfoResponse.builder()
                .openTime(center.getOpenTime().getTime())
                .closeTime(center.getCloseTime().getTime())
                .center(CenterInfoResponseDto.fromEntity(center))
                .date(date.toString())
                .reservedTimes(reservedTimes)
                .build();
    }


    // 특정 체육관의 특정 [ 예약 내역 ] 조회
    @Transactional(readOnly = true)
    public ReservationInfoResponse getReservationInfo(User user, Long centerId, Long reservationId) {

        CenterReservation reservation = centerReservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFound());

        Center center = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        // 예약한 센터랑 가져온센터랑 일치하지 않을경우
        if (!reservation.getCenter().getId().equals(centerId)) {
            throw new CenterReservationNotMatch();
        }

        // 예약한 사람이랑 현재 접속자랑 일치하지 않을경우
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new UnAuthorizedAccess();
        }

        return ReservationInfoResponse.fromEntity(reservation);
    }


    // 해당시간 이미 예약됐는지 아닌지 체크 함수
    private boolean isAlreadyReservedTimes(Center center, LocalDate date, List<String> reservingTimes) {
        List<ReservingTime> reservedTimes = new ArrayList<>();
        centerReservationRepository
                .findAllByCenterAndReservingDate(center, date)
                .forEach(reservation -> reservedTimes.addAll(reservation.getReservingTimes()));

        for (String time : reservingTimes) {
            try {
                if (reservedTimes.contains(ReservingTime.findTime(time))) {
                    return true;
                }
            } catch (Exception e) {
                throw new TimeFormatNotAccepted();
            }
        }
        return false;
    }

    // 해당 체육관 금액 조회
    public PriceResponse getPrice(Long stadiumId, LocalDate date, CreateReservationRequest request) {
        Center center = centerRepository.findById(stadiumId).orElseThrow(() -> new CenterNotFound());

        int stadiumPrice = center.getPrice() * request.getReservingTimes().size();

        return PriceResponse.builder()
                .price(stadiumPrice)
                .build();
    }

    // 예약상태 업데이트 -> 만료
    @Transactional
    public void updateExpiredReservations() {
        LocalDate currentDate = LocalDate.now();
        List<CenterReservation> CenterReservations = centerReservationRepository.findByStatus(CenterReservationStatus.RESERVED);
        for (CenterReservation centerReservation : CenterReservations) {
            if (centerReservation.getReservingDate().isBefore(currentDate)) {
                centerReservation.expiredReservation();
            }
        }
    }

    // 포인트 비교
    public boolean pointComparison(User user,CenterReservation centerReservation){
        if(user.getPoint() < centerReservation.getPrice()){
            return false; // lack
        }
        return true;
    }
}
