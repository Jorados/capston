package jorados.capston.dto.response;


import jorados.capston.domain.type.CenterStatus;
import jorados.capston.domain.type.ReservingTime;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class CenterResponse {

    private String center_name;

    @Enumerated(EnumType.STRING)
    private CenterStatus status; // 시설 예약 가능상태

    @Enumerated(EnumType.STRING)
    private ReservingTime openTime; //오픈시간

    @Enumerated(EnumType.STRING)
    private ReservingTime closeTime; //닫는시간

    private double lat;
    private double lng;


    @Builder
    public CenterResponse(String center_name, CenterStatus status, ReservingTime openTime, ReservingTime closeTime, double lat, double lng) {
        this.center_name = center_name;
        this.status = status;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.lat = lat;
        this.lng = lng;
    }
}
