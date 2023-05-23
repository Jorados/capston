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

    private Long memberId;

    private String name;

    private Double lat;

    private Double lnt;

    private String address;

    private String phone;

    private Integer price;

    //private List<StadiumItemDto.Response> rentalItems;

    private List<CenterImgDto> imgs;

    private String openTime;

    private String closeTime;


    public static CenterInfoResponseDto fromEntity(Center center) {
        return CenterInfoResponseDto.builder()
                .id(center.getId())
                .memberId(center.getUser().getId())
                .name(center.getCenter_name())
                .lat(center.getLat())
                .lnt(center.getLng())
                //.phone(stadium.getPhone())
                .address(center.getAddress())
                .price(center.getPrice())
                .openTime(center.getOpenTime().getTime())
                .closeTime(center.getCloseTime().getTime())
                .imgs(center.getImgs().isEmpty() ?
                        null :
                        center.getImgs().stream().map(img ->
                                        CenterImgDto.builder()
                                                .id(img.getId())
                                                .publicId(img.getImg_Id())
                                                .imgUrl(img.getImg_Url())
                                                .build())
                                .collect(Collectors.toList()))
                .build();
    }
}
