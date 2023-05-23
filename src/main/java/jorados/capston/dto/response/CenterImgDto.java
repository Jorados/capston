package jorados.capston.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenterImgDto {
    private Long id;
    private String publicId;
    private String imgUrl;
}
