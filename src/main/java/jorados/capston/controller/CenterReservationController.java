package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.exception.CenterNotFound;
import jorados.capston.repository.CenterRepository;
import jorados.capston.request.CenterEdit;
import jorados.capston.response.CenterResponse;
import jorados.capston.service.CenterService;
import jorados.capston.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/center")
public class CenterReservationController {

    private final UserService userService;
    private final CenterService centerService;
    private final CenterRepository centerRepository;

    // 센터 예약하기 -> create / @Post
    @PostMapping("/reserve")
    public void CenterReserveCreate(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid Center center){
        User findUser = principalDetails.getUser();
        centerService.CenterReserveSave(findUser,center);
    }

    // 센터 예약정보 조회 -> read / @Get
    @GetMapping("/reserve")
    public List<Center> CenterReserveRead(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        List<Center> findReserveCenter = centerService.CenterReserveRead(findUser);
        return findReserveCenter;
    }

    // 센터 예약정보수정 -> update / @Patch
    // 센터는 예약정보를 수정하면 -> 예약시간을 바꾸는 거 겠지? -> 회의 필요
    @PutMapping("/{centerId}/update")
    public void CenterReserveUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId){
        // 이 예약 센터가 현재 인증된 사용자랑 일치하는지아닌지 판별 후에 수정.
    }

    // 특정 센터 예약취소 -> delete / @Delete
    @DeleteMapping("/{centerId}/delete")
    public void CenterReserveDelete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long centerId){
        // 이 예약 센터가 현재 인증된 사용자랑 일치하는지아닌지 판별 후에 삭제.
        User findUser = principalDetails.getUser();
        Center findCenter = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());

        if(findCenter.getUser().getId() == findUser.getId()){
            centerService.CenterReserveDelete(findCenter.getId());
        }
        else log.info("삭제 에러 : 회원 정보 , 예약 정보 불일치");
    }

}
