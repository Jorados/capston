package jorados.capston.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String content;

    @Builder
    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
