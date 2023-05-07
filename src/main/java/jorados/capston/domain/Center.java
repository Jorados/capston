package jorados.capston.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jorados.capston.domain.type.CenterStatus;
import jorados.capston.domain.type.ReservingTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Center {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="center_id")
    private Long id; // DB 넘버
    private String center_name; // 시설 이름
    private String address; // 주소
    private String detail_address; // 상세주소

    @Column(name = "center_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CenterStatus status; // 예약상태

    @Column(name = "open_time", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservingTime openTime; //오픈시간

    @Column(name = "close_time", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservingTime closeTime; //닫는시간

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> user = new ArrayList<>(); // 한 시설에 여러 회원 예약 가능 (일대다)

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Center_Img> imgs = new ArrayList<>(); // 한 시설에 여러장의 사진 (일대다)


    @Builder
    public Center(Long id, String center_name, String address, String detail_address, CenterStatus status, ReservingTime openTime, ReservingTime closeTime) {
        this.id = id;
        this.center_name = center_name;
        this.address = address;
        this.detail_address = detail_address;
        this.status = status;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
