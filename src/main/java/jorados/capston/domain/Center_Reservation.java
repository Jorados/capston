package jorados.capston.domain;


import jorados.capston.domain.type.ReservingTime;
import lombok.Getter;

import javax.persistence.*;
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
    private LocalDateTime reserve_time; // 예약을 한 현재시간
    private ReservingTime reservingTimes; // 예약 시간

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;
}
