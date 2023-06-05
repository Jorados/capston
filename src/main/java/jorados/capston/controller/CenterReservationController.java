package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.User;
import jorados.capston.repository.CenterRepository;
import jorados.capston.service.CenterReservationService;
import jorados.capston.service.CenterService;
import jorados.capston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    // 센터 예약 수정
    @PatchMapping("/{centerId}/reservation/{reservationId}")
    public ResponseEntity<?> executeReservation(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId, @PathVariable Long reservationId) {
        User findUser = principalDetails.getUser();
        centerReservationService.executeReservation(findUser, centerId, reservationId);
        return ResponseEntity.ok().build();
    }

    // 센터 예약 취소
    @DeleteMapping("/{centerId}/reservation/{reservationId}")
    public ResponseEntity<?> deleteReservation(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId, @PathVariable Long reservationId) {
        User findUser = principalDetails.getUser();
        centerReservationService.deleteReservation(findUser, centerId, reservationId);

        log.info("회원 번호 [ " + findUser.getId() + " ] 로 예약이 취소되었습니다..");
        return new ResponseEntity<>("예약이 취소되었습니다.",null,HttpStatus.OK);
    }


    /*****************************************  조회  ***********************************************************/

    // 내 예약목록 -> /center/reservations?page=1&size=2 이런식으로 page 객체 파라미터 보내야함
    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations(@AuthenticationPrincipal PrincipalDetails principalDetails, Pageable pageable) {
        User findUser = principalDetails.getUser();
        Page<ReservationResponse> reservations = centerReservationService.getAllReservationsByUser(findUser, pageable);
        return ResponseEntity.ok().body(reservations);
    }

    // 내 예약내역 상세보기
    @GetMapping("/{centerId}/reservation/{reservationId}")
    public ResponseEntity<?> getReservationInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId, @PathVariable Long reservationId) {
        User findUser = principalDetails.getUser();
        ReservationInfoResponse reservationInfo = centerReservationService.getReservationInfo(findUser, centerId, reservationId);

        return ResponseEntity.ok().body(reservationInfo);
    }

    // 체육관 예약 페이지 정보 요청
    @GetMapping("/{centerId}/reservation")
    public ResponseEntity<?> getCenterReservationInfo(@PathVariable Long centerId) {

        LocalDate date = LocalDate.now();
        ReservationCenterInfoResponse reservationInfo = centerReservationService.getStadiumReservationInfo(centerId, date);
        return ResponseEntity.ok().body(reservationInfo);
    }

    // 체육관 가격 조회 : 미완

}
