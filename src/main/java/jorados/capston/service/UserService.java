package jorados.capston.service;


import jorados.capston.domain.User;
import jorados.capston.domain.UserEnum;
import jorados.capston.exception.DuplicateException;
import jorados.capston.exception.UserNotFound;
import jorados.capston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Transactional
    public void join(User user){
        validateDuplicateMember(user);

        String encPassword = passwordEncoder.encode(user.getPassword());
        User saveUser = User.builder()
                .username(user.getUsername())
                .password(encPassword)
                .email(user.getEmail())
                .role(UserEnum.CUSTOMER)
                .build();
        userRepository.save(saveUser);
        log.info("회원 가입 완료");
    }

    public List<User> AllUser(){
        return userRepository.findAll();
    }

    private void validateDuplicateMember(User user) {
        List<User> findAllUser = userRepository.findAllByUsername(user.getUsername());

        //userName겹치는 경우
        if (!findAllUser.isEmpty()) {
            log.info("회원이름 중복 회원 발생");
            throw new DuplicateException();
        }
    }
}
