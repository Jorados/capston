package jorados.capston.dto.response;


import jorados.capston.domain.Center;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterInfoResponseDto {
    private Long id;

    private Long userId;

    private String name;

    private Double lat;

    private Double lnt;

    private String address;

    private Integer price;

    private String imgUrl;

    private String openTime;

    private String closeTime;


    public static CenterInfoResponseDto fromEntity(Center center) {
        return CenterInfoResponseDto.builder()
                .id(center.getId())
                //.userId(center.getUser().getId())
                .name(center.getCenter_name())
                .lat(center.getLat())
                .lnt(center.getLng())
                .address(center.getAddress())
                .price(center.getPrice())
                .openTime(center.getOpenTime().getTime())
                .closeTime(center.getCloseTime().getTime())
                .imgUrl(center.getImgUrl())
                .build();
    }
}
