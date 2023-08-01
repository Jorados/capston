package jorados.capston;

import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.domain.type.CenterStatus;
import jorados.capston.domain.type.ReservingTime;
import jorados.capston.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class initCenter {
    private final InitCenterService initCenterService;

    @PostConstruct
    public void init(){
        initCenterService.init();
    }

    @Component
    static class InitCenterService{
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init(){
            Center center1 = Center.builder()
                    .center_name("칠암캠운동장")
                    .lat(35.181368)
                    .lng(128.092885)
                    .imgUrl("https://mblogthumb-phinf.pstatic.net/MjAyMTA2MTNfMTM4/MDAxNjIzNTc1MDA4NDI1.6o9PlI70Y9Cq9ecviMjCXdojIqBspio84OWobfx0avAg.24uRy6OE3_6lO-fLsCAtEEuTq3SsABzpSDlO3dgQ0usg.JPEG.scnn9/20210613%EF%BC%BF174933.jpg?type=w800")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center1);

            Center center2 = Center.builder()
                    .center_name("칠암캠체육관")
                    .lat(35.180550)
                    .lng(128.092695)
                    .imgUrl("https://mblogthumb-phinf.pstatic.net/MjAyMTAxMTVfMjU2/MDAxNjEwNjcwMTEyMDM0.hxM1ihaUmNTbmEzbyB7h8-lXYNayyUcHjVdcHePDr94g.2TzU8z1HjWs-eFNw5Qf-Z2ZBfizGViJjTE0rpLYVlEwg.JPEG.bindustrycop/%25EA%25B2%25BD%25EB%2582%25A8_%25EC%25A7%2584%25EC%25A3%25BC_%25EA%25B2%25BD%25EB%2582%25A8%25EA%25B3%25BC%25ED%2595%2599%25EA%25B8%25B0%25EC%2588%25A0%25EB%258C%2580_%25EC%2595%2588%25EC%25A0%2584%25ED%258C%25A8%25EB%2594%25A9_(9).jpg?type=w800")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center2);

            Center center3 = Center.builder()
                    .center_name("칠암캠농구장")
                    .lat(35.181707)
                    .lng(128.092445)
                    .imgUrl("https://i.ytimg.com/vi/QD7UzaTO45c/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgXihVMA8=&rs=AOn4CLAbo1UI7pIg4xk-YhXJJJIaMpaVmw")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center3);

            Center center4 = Center.builder()
                    .center_name("칠암캠풋살장")
                    .lat(35.181853)
                    .lng(128.092303)
                    .imgUrl("https://www.sisul.or.kr/open_content/skydome/images/photo_futsal01.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center4);

            Center center5 = Center.builder()
                    .center_name("칠암캠테니스장")
                    .lat(35.181990)
                    .lng(128.093008)
                    .imgUrl("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F1722EA4D5155AA2B14")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center5);

            Center center6 = Center.builder()
                    .center_name("칠암캠족구장")
                    .lat(35.181878)
                    .lng(128.092629)
                    .imgUrl("http://www.gndomin.com/news/photo/201906/209263_208618_4955.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center6);

            Center center7 = Center.builder()
                    .center_name("가좌캠풋살장")
                    .lat(35.154370)
                    .lng(128.103407)
                    .imgUrl("https://www.gnunews.kr/news/photo/201911/8757_51_0000.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center7);

            Center center8 = Center.builder()
                    .center_name("가좌캠교직원테니스장")
                    .lat(35.156537)
                    .lng(128.102683)
                    .imgUrl("https://www.gnu.ac.kr/upload//campus/img_44897945-9bf7-40b2-97c8-4238642e9d181675164473050.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center8);

            Center center9 = Center.builder()
                    .center_name("가좌캠테니스장")
                    .lat(35.154181)
                    .lng(128.102844)
                    .imgUrl("https://mblogthumb-phinf.pstatic.net/MjAyMjEyMDdfMTY1/MDAxNjcwMzc5ODIwMDY3.GqQchV3csJj3p57F0yE0B9roG_p4iMCJYDsEwCl7nQUg.T85b5phBAPTnU4SX-1G-kOcxemN_jYH6pe9lrdDPkxYg.PNG.capminjuni/image.png?type=w800")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center9);

            Center center10 = Center.builder()
                    .center_name("가좌캠족구장")
                    .lat(35.154658)
                    .lng(128.102755)
                    .imgUrl("http://www.gndomin.com/news/photo/201906/209263_208618_4955.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center10);

            Center center11 = Center.builder()
                    .center_name("가좌캠농구장")
                    .lat(35.154627)
                    .lng(128.102903)
                    .imgUrl("https://i.ytimg.com/vi/QD7UzaTO45c/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgXihVMA8=&rs=AOn4CLAbo1UI7pIg4xk-YhXJJJIaMpaVmw")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center11);

            Center center12 = Center.builder()
                    .center_name("가좌캠대운동장")
                    .lat(35.154850)
                    .lng(128.104504)
                    .imgUrl("https://www.gnu.ac.kr/upload//campus/img_98287e4a-5e67-4985-bde8-7ffde34728f01675164411173.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center12);

            Center center13 = Center.builder()
                    .center_name("가좌캠운동장")
                    .lat(35.151364)
                    .lng(128.100821)
                    .imgUrl("https://www.gnu.ac.kr/upload//campus/img_a8e3b39a-e912-41e3-85ca-7a5d2346ff791667882330403.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center13);

            Center center14 = Center.builder()
                    .center_name("가좌캠체육관")
                    .lat(35.155414)
                    .lng(128.103060)
                    .imgUrl("https://www.gnu.ac.kr/upload//campus/img_fd9559e2-5262-459f-a2e7-4edade9eaa0e1667881475043.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center14);

            Center center15 = Center.builder()
                    .center_name("통영캠운동장1")
                    .lat(34.838744)
                    .lng(128.399730)
                    .imgUrl("https://mblogthumb-phinf.pstatic.net/MjAxODEwMjhfMTgy/MDAxNTQwNzIxNTQzNTYx.M1tQpUPK5dyoBIC9YPMAJB5wrYiAhqMDSj0T2UNouesg.2PKE05z16r8EdR1zxi5UM0E9PBkwXNXao7d8yrNGFXMg.JPEG.kmu2333/20180213_115841.jpg?type=w800")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center15);

            Center center16 = Center.builder()
                    .center_name("통영캠운동장2")
                    .lat(34.836729)
                    .lng(128.400170)
                    .imgUrl("https://mblogthumb-phinf.pstatic.net/MjAxODEwMjhfMjcg/MDAxNTQwNzIxNTQ0MzIy.ZYxmLmI0w64kSA0Gv6W2TU6BJAtifq2fYCChaZbLHLcg.9xkpIYwAlPYAw0uysZGWFOjovtJ8jN5vMvKGKoowe9gg.JPEG.kmu2333/20180213_115910.jpg?type=w800")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center16);

            Center center17 = Center.builder()
                    .center_name("통영캠체육관")
                    .lat(34.83802)
                    .lng(128.399127)
                    .imgUrl("https://www.gnu.ac.kr/upload//campus/img_1efdf209-be0d-4ec6-8285-d732eaae2ad51673411867261.jpg")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center17);

            Center center18 = Center.builder()
                    .center_name("통영캠테니스장")
                    .lat(34.838553)
                    .lng(128.398888)
                    .imgUrl("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F1722EA4D5155AA2B14")
                    .address("진주시 동진로 33")
                    .openTime(ReservingTime.RT19)
                    .closeTime(ReservingTime.RT37)
                    .price(10000)
                    .status(CenterStatus.POSSIBLE)
                    .build();

            em.persist(center18);
        }
    }
}
