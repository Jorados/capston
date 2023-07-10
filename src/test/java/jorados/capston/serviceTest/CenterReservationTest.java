package jorados.capston.serviceTest;


import jorados.capston.domain.Center;
import jorados.capston.domain.CenterReservation;
import jorados.capston.domain.User;
import jorados.capston.domain.type.CenterReservationStatus;
import jorados.capston.domain.type.ReservingTime;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.dto.CenterReservationDto;
import jorados.capston.exception.UnAuthorizedAccess;
import jorados.capston.repository.CenterRepository;
import jorados.capston.repository.CenterReservationRepository;
import jorados.capston.repository.UserRepository;
import jorados.capston.service.CenterReservationService;
import jorados.capston.service.CenterService;
import jorados.capston.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class CenterReservationTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CenterService centerService;
    @Autowired
    CenterRepository centerRepository;

    @Autowired
    CenterReservationService centerReservationService;
    @Autowired
    CenterReservationRepository centerReservationRepository;

    @BeforeEach
    void deleteAll(){
        userRepository.deleteAll();
        centerRepository.deleteAll();
        centerReservationRepository.deleteAll();;
    }

    @Test
    @DisplayName("센터 예약 생성,조회 테스트")
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

        List<ReservingTime> reservingTimes = new ArrayList<>(
                List.of(ReservingTime.RT20, ReservingTime.RT21)
        );

        CenterReservation centerReservation = CenterReservation.builder()
                .center(center)
                .user(user)
                .reservingDate(LocalDate.now())
                .reservingTimes(reservingTimes)
                .price(10000)
                .headCount(3)
                .status(CenterReservationStatus.RESERVED)
                .build();
        centerReservationRepository.save(centerReservation);

        //when
        CenterReservationDto.ReservationInfoResponse findReservation = centerReservationService.getReservationInfo(user, center.getId(), centerReservation.getId());

        //then
        Assertions.assertThat(centerReservation.getCenter().getId()).isEqualTo(findReservation.getCenterId());
        Assertions.assertThat(centerReservationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("센터 예약 삭제 - 권한 테스트")
    public void test2(){
        //given
        User user = User.builder()
                .email("email")
                .username("Name")
                .password("password")
                .role(UserEnum.CUSTOMER)
                .build();
        userRepository.save(user);

        User user2 = User.builder()
                .email("email2")
                .username("Name2")
                .password("password2")
                .role(UserEnum.CUSTOMER)
                .build();
        userRepository.save(user2);


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

        List<ReservingTime> reservingTimes = new ArrayList<>(
                List.of(ReservingTime.RT20, ReservingTime.RT21)
        );

        CenterReservation centerReservation = CenterReservation.builder()
                .center(center)
                .user(user)
                .reservingDate(LocalDate.now())
                .reservingTimes(reservingTimes)
                .price(10000)
                .headCount(3)
                .status(CenterReservationStatus.RESERVED)
                .build();
        centerReservationRepository.save(centerReservation);

        //when
        UnAuthorizedAccess exception = assertThrows(UnAuthorizedAccess.class, () -> {
            centerReservationService.deleteReservation(user2, center.getId(), centerReservation.getId());
        });

        //then
        assertEquals("접근 권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("모든 예약 정보 조회")
    public void test3(){
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

        List<ReservingTime> reservingTimes = new ArrayList<>(
                List.of(ReservingTime.RT20, ReservingTime.RT21)
        );

        CenterReservation centerReservation = CenterReservation.builder()
                .center(center)
                .user(user)
                .reservingDate(LocalDate.now())
                .reservingTimes(reservingTimes)
                .price(10000)
                .headCount(3)
                .status(CenterReservationStatus.RESERVED)
                .build();
        centerReservationRepository.save(centerReservation);

        //when
        Pageable pageable = PageRequest.of(0, 5);
        Page<CenterReservationDto.ReservationResponse> reservations = centerReservationService.getAllReservationsByUser(user, pageable);

        //then
        Assertions.assertThat(reservations.getTotalElements()).isEqualTo(1);
    }

}
