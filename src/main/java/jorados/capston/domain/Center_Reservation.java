package jorados.capston.domain;


import jorados.capston.domain.type.CenterStatus;
import jorados.capston.domain.type.ReservingTime;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Center_Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_reservation_id")
    private Long id; // DB 넘버
    private int price; // 가격

    @Column(nullable = false)
    private LocalDateTime reserve_time; // 예약을 한 현재시간

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CenterStatus status;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate reservingDate;

    @Builder.Default
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<ReservingTime> reservingTimes = new ArrayList<>();

}
