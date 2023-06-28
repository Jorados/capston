package jorados.capston.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jorados.capston.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //프록시객체무시하고 원본객체 직렬화
    private User user;

    @Builder
    public PostResponse(Long id,String title, String content, User user) {
        this.id=id;
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
