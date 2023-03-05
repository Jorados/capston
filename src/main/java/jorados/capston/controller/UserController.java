package jorados.capston.controller;


import jorados.capston.domain.User;
import jorados.capston.response.ResponseDto;
import jorados.capston.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //login,logout 기능은 일단 스프링시큐리티에서 제공

    @PostMapping("/home")
    public String home(){
        return "<h1>홈<h1>";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid User user){
        userService.join(user);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", user.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping("/api/user/1")
    public String customerPage(){
        return "<h1>일반회원 페이지<h1>";
    }

    @GetMapping("/api/admin/1")
    public String adminPage(){
        return "<h1>관리자 페이지<h1>";
    }



}
