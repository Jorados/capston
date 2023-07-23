package jorados.capston.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jorados.capston.domain.type.CenterReservationStatus;
import jorados.capston.domain.type.CenterStatus;
import jorados.capston.domain.type.ReservingTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Center implements Serializable {

    /**
     *  속성
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="center_id")
    private Long id; // DB 넘버
    private String center_name; // 시설 이름
//    private String address; // 주소
//    private String detail_address; // 상세주소

    private double lat;
    private double lng;

    //@Column(name = "center_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CenterStatus status; // 시설 예약 가능상태

    @Column(name = "open_time", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservingTime openTime; //오픈시간

    @Column(name = "close_time", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservingTime closeTime; //닫는시간

    private Integer price;
    private String address;

//    @Column(name = "img_id")
//    private String imgId;

    @Column(name = "img_url", length = 1000)
    private String imgUrl;


    /**
     *  연관관계 매핑
     */

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CenterReservation> center_reservations = new ArrayList<>(); // 한 시설에 예약 여러 건 (일대 다)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 생성자
     */

    public void setUser(User user) {
        this.user = user;
        user.getCenter().add(this);
    }

    @Builder
    public Center(Long id, String center_name, double lat, double lng, CenterStatus status, ReservingTime openTime, ReservingTime closeTime, User user,String address,Integer price,String imgUrl) {
        this.id = id;
        this.center_name = center_name;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.user = user;
        this.address = address;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
