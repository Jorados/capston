package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.dto.CenterReservationDto;
import jorados.capston.exception.CenterNotFound;
import jorados.capston.repository.CenterRepository;
import jorados.capston.service.CenterService;
import jorados.capston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jorados.capston.dto.CenterReservationDto.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/center")
public class CenterReservationController {

    private final UserService userService;
    private final CenterService centerService;
    private final CenterRepository centerRepository;

    // 예약하기
    @PostMapping("/{centerId}/reservation")
    public ResponseEntity<?> createReservation(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId, @RequestBody CreateReservationRequest request) {

        User user = principalDetails.getUser();
        CenterReservationDto.CreateReservationResponse reservationInfo = centerReservationService.createReservation(user, centerId, request);

        notificationService.createNotification(RESERVATION, stadiumId, "체육관 [ " + reservationInfo.getStadiumName() + " ]에 새로운 예약이 등록되었습니다.", user);
        log.info("회원 번호 [ " + user.getId() + " ] 로 알람이 발송되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationInfo);
    }

    // 센터 예약 수정
    @PatchMapping("/{centerId}/reservation/{reservationId}")
    public void CenterReserveUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId){
        // 이 예약 센터가 현재 인증된 사용자랑 일치하는지아닌지 판별 후에 수정.
    }

    // 센터 예약 취소
    @DeleteMapping("/{centerId}/reservation/{reservationId}")
    public void CenterReserveDelete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId){
        // 이 예약 센터가 현재 인증된 사용자랑 일치하는지아닌지 판별 후에 삭제.
        User findUser = principalDetails.getUser();
        Center findCenter = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        if(findCenter.getUser().getId() == findUser.getId()){
            centerService.CenterReserveDelete(findCenter.getId());
        }
        else log.info("삭제 에러 : 회원 정보 , 예약 정보 불일치");
    }


    /****************************************************************************************************/

    // 내 예약목록
    @GetMapping("/reservations")
    public List<Center> CenterReserveRead(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        List<Center> findReserveCenter = centerService.CenterReserveRead(findUser);
        return findReserveCenter;
    }

    // 체육관 예약 페이지

    // 체육관 예약 상세 내역 조회

    // 체육관 가격 조회

}
