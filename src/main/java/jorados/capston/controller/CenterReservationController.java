package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.dto.CenterReservationDto;
import jorados.capston.exception.CenterNotFound;
import jorados.capston.repository.CenterRepository;
import jorados.capston.service.CenterReservationService;
import jorados.capston.service.CenterService;
import jorados.capston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final CenterReservationService centerReservationService;

    // 센터 예약하기
    @PostMapping("/{centerId}/reservation")
    public ResponseEntity<?> createReservation(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @PathVariable Long centerId,
                                               @RequestBody CreateReservationRequest request) {

        User user = principalDetails.getUser();
        CreateReservationResponse reservationInfo = centerReservationService.createReservation(user, centerId, request);

        log.info("회원 번호 [ " + user.getId() + " ] 로 예약 되었습니다..");
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationInfo);
    }

    // 센터 예약 수정 : 미완
    @PatchMapping("/{centerId}/reservation/{reservationId}")
    public void CenterReserveUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId){
        // 이 예약 센터가 현재 인증된 사용자랑 일치하는지아닌지 판별 후에 수정.
    }

    // 센터 예약 취소
    @DeleteMapping("/{centerId}/reservation/{reservationId}")
    public ResponseEntity<?> deleteReservation(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId, @PathVariable Long reservationId) {
        User findUser = principalDetails.getUser();
        centerReservationService.deleteReservation(findUser, centerId, reservationId);

        log.info("회원 번호 [ " + findUser.getId() + " ] 로 예약이 취소되었습니다..");
        return ResponseEntity.ok().build();
    }


    /*****************************************  조회  ***********************************************************/

    // 내 예약목록 -> /center/reservations?page=1&size=2 이런식으로 page 객체 파라미터 보내야함
    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations(@AuthenticationPrincipal PrincipalDetails principalDetails, Pageable pageable) {
        User findUser = principalDetails.getUser();
        Page<ReservationResponse> reservations = centerReservationService.getAllReservationsByUser(findUser, pageable);
        return ResponseEntity.ok().body(reservations);
    }

    // 체육관 예약 페이지 : 미완

    // 체육관 특정 예약 내역 조회
    @GetMapping("/{centerId}/reservation/{reservationId}")
    public ResponseEntity<?> getReservationInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId, @PathVariable Long reservationId) {
        User findUser = principalDetails.getUser();
        ReservationInfoResponse reservationInfo = centerReservationService.getReservationInfo(findUser, centerId, reservationId);

        return ResponseEntity.ok().body(reservationInfo);
    }

    // 체육관 가격 조회 : 미완

}
