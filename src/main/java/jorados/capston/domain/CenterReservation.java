package jorados.capston.domain;


import jorados.capston.domain.type.ReservingTime;
import jorados.capston.domain.type.CenterReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jorados.capston.dto.CenterReservationDto.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CenterReservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_reservation_id")
    private Long id; // DB 넘버
    private int price; // 가격

//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime reserve_time; // 예약을 한 현재시간

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CenterReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate reservingDate; // 예약을 한 날짜

    @Builder.Default
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<ReservingTime> reservingTimes = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 인원수
    private int headCount;

    public void cancelReservation() {
        this.status = CenterReservationStatus.CANCELED;
    }

    public static CenterReservation fromRequest(Center center, User user, CreateReservationRequest request, int price) {
        return CenterReservation.builder()
                .center(center)
                .user(user)
                .reservingDate(LocalDate.now())
                .reservingTimes(request.getReservingTimes().stream()
                        .map(time -> ReservingTime.findTime(time))
                        .collect(Collectors.toList()))
                .price(price)
                .headCount(request.getHeadCount())
                .status(CenterReservationStatus.RESERVED)
                //.paymentType(PaymentType.valueOf(request.getPaymentType()))
                .build();
    }

}