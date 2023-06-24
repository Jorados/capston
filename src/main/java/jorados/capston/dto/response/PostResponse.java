package jorados.capston.dto.response;


import jorados.capston.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class PostResponse {
    private String title;
    private String content;
    private User user;

    @Builder
    public PostResponse(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
