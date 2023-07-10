package jorados.capston.serviceTest;


import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.domain.type.ReservingTime;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.dto.response.CenterInfoResponseDto;
import jorados.capston.exception.CenterNotFound;
import jorados.capston.repository.CenterRepository;
import jorados.capston.repository.UserRepository;
import jorados.capston.service.CenterService;
import jorados.capston.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CenterServiceTest {
    @Autowired
    CenterService centerService;

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void deleteAll(){
        centerRepository.deleteAll();
    }

    /**
     * center 정보를 임의의 더미 데이터로 넣고 시작했기에, center에 대한 테스트는 따로 생략.
     */

    @Test
    @DisplayName("센터 생성하기")
    public void test1(){
        //given
        User user = User.builder()
                .email("email")
                .username("Name")
                .password("password")
                .role(UserEnum.CUSTOMER)
                .build();
        userRepository.save(user);


        Center center = Center.builder()
                .center_name("center")
                .lat(37.5)
                .lng(127.5)
                .address("address")
                .price(10000)
                .imgUrl("Image URL")
                .openTime(ReservingTime.findTime("09:00"))
                .closeTime(ReservingTime.findTime("18:00"))
                .user(user)
                .build();
        centerRepository.save(center);

        //when
        Center findCenter = centerRepository.findById(center.getId()).orElseThrow(() -> new CenterNotFound());

        //then
        assertThat(center.getCenter_name()).isEqualTo(findCenter.getCenter_name());
        assertThat(center.getAddress()).isEqualTo(findCenter.getAddress());
    }

    @Test
    @DisplayName("내가 생성한 센터 조회")
    public void test2(){
        //given
        User user = User.builder()
                .email("email")
                .username("Name")
                .password("password")
                .role(UserEnum.CUSTOMER)
                .build();
        userRepository.save(user);


        Center center = Center.builder()
                .center_name("center")
                .lat(37.5)
                .lng(127.5)
                .address("address")
                .price(10000)
                .imgUrl("Image URL")
                .openTime(ReservingTime.findTime("09:00"))
                .closeTime(ReservingTime.findTime("18:00"))
                .user(user)
                .build();
        centerRepository.save(center);

        //when
        CenterInfoResponseDto centerInfo = centerService.getCenterInfo(center.getId(), user);

        //then
        assertThat(centerInfo.getId()).isEqualTo(center.getId());
        assertThat(centerInfo.getAddress()).isEqualTo(center.getAddress());
    }


}
