package jorados.capston.serviceTest;


import jorados.capston.domain.User;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.exception.DuplicateException;
import jorados.capston.exception.DuplicateExceptionNickname;
import jorados.capston.exception.UnAuthorizedAccess;
import jorados.capston.repository.UserRepository;
import jorados.capston.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void deleteAll(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 - 아이디 중복테스트")
    public void test1(){
        User user = User.builder()
                .username("aaa")
                .password("aaa")
                .email("aaa")
                .role(UserEnum.CUSTOMER)
                .nickname("aaa")
                .build();

        userService.join(user);

        User user2 = User.builder()
                .username("aaa")
                .password("aaa")
                .email("aaa")
                .role(UserEnum.CUSTOMER)
                .nickname("aaa2")
                .build();

        //when
        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            userService.join(user2);
        });

        //then
        assertEquals("이미 존재하는 회원아이디입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원 가입 - 닉네임 중복테스트")
    public void test2(){
        User user = User.builder()
                .username("aaa")
                .password("aaa")
                .email("aaa")
                .role(UserEnum.CUSTOMER)
                .nickname("aaa")
                .build();

        userService.join(user);

        User user2 = User.builder()
                .username("aaa2")
                .password("aaa")
                .email("aaa")
                .role(UserEnum.CUSTOMER)
                .nickname("aaa")
                .build();

        //when
        DuplicateExceptionNickname exception = assertThrows(DuplicateExceptionNickname.class, () -> {
            userService.join(user2);
        });

        //then
        assertEquals("이미 존재하는 닉네임입니다.", exception.getMessage());
    }
}
