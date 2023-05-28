package jorados.capston.domain.type;

import jorados.capston.domain.Center;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterDocument {
    @Id
    private Long id;
    private String name;
    private Double lat;
    private Double lnt;
    private String address;
    private Integer price;
    private String imgUrl;
    private List<String> tags;


    public static CenterDocument fromEntity(Center center) {
        return CenterDocument.builder()
                .id(center.getId())
                .name(center.getCenter_name())
                .address(center.getAddress())
                .lat(center.getLat())
                .lnt(center.getLng())
                .price(center.getPrice())
                .imgUrl(center.getImgUrl())
                .build();
    }
}
