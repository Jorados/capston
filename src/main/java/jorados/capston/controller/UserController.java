package jorados.capston.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.config.auth.PrincipalDetailsService;
import jorados.capston.domain.User;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.repository.UserRepository;
import jorados.capston.dto.request.UserEdit;
import jorados.capston.dto.response.ResponseDto;
import jorados.capston.dto.response.UserResponse;
import jorados.capston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid User user){
        userService.join(user);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", user.getUsername()), HttpStatus.CREATED);
    }

    //권한 테스트
    @GetMapping("/api/user/1")
    public ResponseEntity<?> customerPage() {
        return new ResponseEntity<>("일반 회원 페이지",null,HttpStatus.OK);
    }

    //권한 테스트
    @GetMapping("/api/admin/1")
    public ResponseEntity<?> adminPage(){
        return new ResponseEntity<>("관리자 회원 페이지",null,HttpStatus.OK);
    }

    //회원 싹 다 조회
    @GetMapping("/user/all")
    public List<User> read(){
        return userService.AllUser();
    }

    //특정 회원 조회 --> 파라미터값으로 userId 필요
    @GetMapping("/user/{userId}")
    public UserResponse findUser(@PathVariable Long userId){
        return userService.read(userId);
    }

    //회원 수정 --> 파라미터값으로 userId 필요
    @PatchMapping("/user/{userId}")
    public void updateUser(@PathVariable Long userId, UserEdit userEdit){
        userService.update(userId,userEdit);
    }

    //회원 삭제 --> 파라미터값으로 userId 필요
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.delete(userId);
    }

    //로그인 성공 -> 유저 정보 일부 반환
    @GetMapping("/user/success")
    public UserResponse success(@AuthenticationPrincipal PrincipalDetails loginUser){
        UserResponse userResponse = UserResponse.builder()
                .id(loginUser.getUser().getId())
                .username(loginUser.getUser().getUsername())
                .email(loginUser.getUser().getEmail())
                .nickname(loginUser.getUser().getNickname())
                .role(UserEnum.CUSTOMER)
                .point(loginUser.getUser().getPoint())
                .build();
        return userResponse;
    }

    // 접속 회원 닉네임 수정
    @PatchMapping("/user/update")
    public ResponseEntity<?> userUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserEdit userEdit){
        Long UserId = principalDetails.getUser().getId();
        userService.nickNameUpdate(UserId,userEdit);
        return ResponseEntity.status(HttpStatus.OK).body("수정완료 되었습니다.");
    }

    // 회원 포인트 충전
    @PatchMapping("/user/point")
    public ResponseEntity<?> userPoint(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestParam(name="chargePoint") int chargePoint){
        User findUser = principalDetails.getUser();
        userService.ChargePoint(findUser.getId(),chargePoint);
        return ResponseEntity.status(HttpStatus.OK).body("포인트 충전이 완료되었습니다");
    }
}
