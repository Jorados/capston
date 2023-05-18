package jorados.capston.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.config.auth.PrincipalDetailsService;
import jorados.capston.domain.User;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.repository.UserRepository;
import jorados.capston.request.UserEdit;
import jorados.capston.response.ResponseDto;
import jorados.capston.response.UserResponse;
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
//@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PrincipalDetailsService principalDetailsService;

    private final AuthenticationManager authenticationManager;

    //login,logout 기능은 일단 스프링시큐리티에서 제공

    @PostMapping("/home")
    public String home(){
        return "<h1>홈<h1>";
    }

    //가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid User user){
        userService.join(user);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", user.getUsername()), HttpStatus.CREATED);
    }

    // 리프레시 토큰 - 예정
//    @PostMapping("/refresh-token")
//    public RefreshTokenResponse generateRefreshToken(@RequestBody final RefreshTokenRequest request) {
//        RefreshTokenResponse refreshTokenResponse = tokenService.generateRefreshToken(request);
//        return refreshTokenResponse;
//    }

    //권한 테스트
    @GetMapping("/api/user/1")
    public void customerPage(HttpServletResponse response) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String str = "일반회원 페이지";
        String responseBody = om.writeValueAsString(str);
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(200);
        response.getWriter().println(responseBody);
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
                .role(UserEnum.CUSTOMER)
                .build();
        return userResponse;
    }
}
