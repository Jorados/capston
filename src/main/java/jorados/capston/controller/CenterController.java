package jorados.capston.controller;


import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.request.CenterEdit;
import jorados.capston.request.UserEdit;
import jorados.capston.response.CenterResponse;
import jorados.capston.response.ResponseDto;
import jorados.capston.response.UserResponse;
import jorados.capston.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/center")
public class CenterController {
    private final CenterService centerService;

    // 저장
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Center center){
        centerService.save(center);
        return new ResponseEntity<>(new ResponseDto<>(1, "저장 성공", center.getCenter_name()), HttpStatus.CREATED);
    }

    // 다 조회
    @GetMapping("/all")
    public List<Center> read(){
        return centerService.findAll();
    }

    // 특정 조회
    @GetMapping("/{centerId}")
    public CenterResponse findCenter(@PathVariable Long centerId){
        return centerService.read_Center(centerId);
    }


    //수정 --> 파라미터값으로 centerId 필요
    @PatchMapping("/{centerId}")
    public void updateCenter(@PathVariable Long centerId, CenterEdit centerEdit){
        centerService.center_update(centerId,centerEdit);
    }

    //삭제 --> 파라미터값으로 userId 필요
    @DeleteMapping("/{centerId}")
    public void deleteCenter(@PathVariable Long centerId){
        centerService.delete_center(centerId);
    }
}
