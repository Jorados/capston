package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.User;
import jorados.capston.exception.UserNotFound;
import jorados.capston.dto.response.CenterInfoResponseDto;
import jorados.capston.dto.response.CenterResponseDto;
import jorados.capston.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/center")
public class CenterController {
    private final CenterService centerService;

    // 다 조회
    @GetMapping("/all")
    public ResponseEntity<Page<CenterResponseDto>> read(Pageable pageable){
        Page<CenterResponseDto> centers = centerService.getAllStadiums(pageable);
        return ResponseEntity.ok().body(centers);
    }

    // 특정 조회
    @GetMapping("/{centerId}/info")
    public ResponseEntity<CenterInfoResponseDto> getCenterInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long centerId
    ) {
        User user = null;
        try {
            user = principalDetails.getUser();
        } catch (Exception e) {
            throw new UserNotFound();
        }
        CenterInfoResponseDto center = centerService.getCenterInfo(centerId, user);
        return ResponseEntity.ok().body(center);
    }

    // 체육관 검색
//    @GetMapping("/search")
//    public ResponseEntity<Page<StadiumResponseDto>> searchStadium(
//            @RequestParam String searchValue,
//            Pageable pageable) {
//        Page<StadiumResponseDto> stadiumDocuments = stadiumSearchService.search(searchValue, pageable);
//        return ResponseEntity.ok().body(stadiumDocuments);
//    }

//    // 저장
//    @PostMapping("/save")
//    public ResponseEntity<?> save(@RequestBody Center center){
//        centerService.save(center);
//        return new ResponseEntity<>(new ResponseDto<>(1, "저장 성공", center.getCenter_name()), HttpStatus.CREATED);
//    }
    //


}
