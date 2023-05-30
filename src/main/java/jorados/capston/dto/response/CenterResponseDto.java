package jorados.capston.dto.response;

import jorados.capston.domain.Center;
import jorados.capston.domain.type.CenterDocument;
import jorados.capston.domain.type.ReservingTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterResponseDto {
    private Long centerId;
    private String name;

    private Double lat;
    private Double lnt;
    private String address;
    private Integer price;

    private String openTime;
    private String closeTime;

    private String imgUrl; // TODO: 이미지주소 + public_id도 포함 필요

    public static CenterResponseDto fromEntity(Center center) {
        return CenterResponseDto.builder()
                .centerId(center.getId())
                .name(center.getCenter_name())
                .lat(center.getLat())
                .lnt(center.getLng())
                .openTime(center.getOpenTime().getTime())
                .closeTime(center.getCloseTime().getTime())
                .address(center.getAddress())
                .price(center.getPrice())
                .imgUrl(center.getImgUrl())
                .build();
    }

    public static CenterResponseDto fromDocument(CenterDocument document) {
        return CenterResponseDto.builder()
                .centerId(document.getId())
                .name(document.getName())
                .lat(document.getLat())
                .lnt(document.getLnt())
                .address(document.getAddress())
                .price(document.getPrice())
                .imgUrl(document.getImgUrl())
                .build();
    }
}
