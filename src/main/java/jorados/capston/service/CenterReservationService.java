package jorados.capston.service;


import jorados.capston.domain.Center;
import jorados.capston.domain.CenterReservation;
import jorados.capston.domain.CenterReservationCancel;
import jorados.capston.domain.User;
import jorados.capston.domain.type.CenterReservationStatus;
import jorados.capston.domain.type.ReservingTime;
import jorados.capston.exception.*;
import jorados.capston.repository.CenterRepository;
import jorados.capston.repository.CenterReservationCancelRepository;
import jorados.capston.repository.CenterReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final CenterReservationCancelRepository centerReservationCancelRepository;

    // 예약하기
    @Transactional
    public CreateReservationResponse createReservation(User user, Long centerId, CreateReservationRequest request) {

        // 1. 센터 찾기
        Center findCenter = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        // 2. 에러처리 ( 겹치는 예약 )
        if (isAlreadyReservedTimes(findCenter, LocalDate.now(), request.getReservingTimes())) {
            throw new AlreadyReservedTime();
        }

        // 3. 예약 생성
        CenterReservation reservation = CenterReservation.fromRequest(findCenter, user, request, getPrice(centerId, LocalDate.now(), request).getPrice());

        // 4. 저장
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
                .date(LocalDate.now())
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

        reservation.cancelReservation();
        CenterReservationCancel reservationCancel = CenterReservationCancel.builder()
                .reservation(reservation)
                .price(reservation.getPrice())
                .build();

        centerReservationRepository.save(reservation);
        centerReservationCancelRepository.save(reservationCancel);
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
}
