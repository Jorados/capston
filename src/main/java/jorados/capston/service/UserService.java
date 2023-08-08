package jorados.capston.service;


import jorados.capston.domain.User;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.exception.DuplicateException;
import jorados.capston.exception.DuplicateExceptionNickname;
import jorados.capston.exception.UserNotFound;
import jorados.capston.repository.UserRepository;
import jorados.capston.dto.request.UserEdit;
import jorados.capston.dto.response.UserResponse;
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
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(UserEnum.CUSTOMER)
                .point(100000)
                .build();
        userRepository.save(saveUser);
        log.info("회원 가입 완료");
    }

    //특정 회원 조회
    public UserResponse read(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        UserResponse userResponse = UserResponse.builder()
                .id(findUser.getId())
                .username(findUser.getUsername())
                .password(findUser.getPassword())
                .nickname(findUser.getNickname())
                .email(findUser.getEmail())
                .build();
        return userResponse;
    }

    //회원 싹 다 조회
    public List<User> AllUser(){
        return userRepository.findAll();
    }

    // 회원 정보 수정
    @Transactional
    public void update(Long userId, UserEdit userEdit){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        findUser.edit(
                userEdit.getUsername() != null ? userEdit.getUsername() : findUser.getUsername(),
                userEdit.getPassword() != null ? userEdit.getPassword() : findUser.getPassword(),
                userEdit.getEmail() != null ? userEdit.getEmail() : findUser.getEmail(),
                userEdit.getNickname() != null ? userEdit.getNickname() : findUser.getNickname()
        );
    }

    // 닉네임 수정
    @Transactional
    public void nickNameUpdate(Long userId,UserEdit userEdit){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        List<User> findAllNickname = userRepository.findAllByNickname(userEdit.getNickname());

        if(!findAllNickname.isEmpty()) {
            log.info("회원 닉네임 중복 발생");
            throw new DuplicateExceptionNickname();
        }
        findUser.nickNameUpdate(userEdit.getNickname());
    }

    @Transactional
    public void PointUpdate(Long userId,int price){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        int finalPoint = findUser.getPoint() - price;
        findUser.priceUpdate(finalPoint);
    }

    @Transactional
    public void ChargePoint(Long userId,int price){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        int finalPoint = findUser.getPoint() + price;
        findUser.priceUpdate(finalPoint);
    }

    @Transactional
    public void CancelPoint(Long userId,int reserveSize){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        int finalPoint = findUser.getPoint() + (10000 * reserveSize);
        findUser.priceUpdate(finalPoint);
    }


    public void delete(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        userRepository.delete(findUser);
    }


    private void validateDuplicateMember(User user) {
        List<User> findAllUser = userRepository.findAllByUsername(user.getUsername());
        List<User> findAllNickname = userRepository.findAllByNickname(user.getNickname());

        //username 겹치는 경우
        if (!findAllUser.isEmpty()) {
            log.info("회원 아이디 중복 발생");
            throw new DuplicateException();
        }

        //nickname 겹치는 경우
        if(!findAllNickname.isEmpty()){
            log.info("회원 닉네임 중복 발생");
            throw new DuplicateExceptionNickname();
        }
    }
}
