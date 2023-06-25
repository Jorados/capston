package jorados.capston.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {

    private String content;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //프록시객체무시하고 원본객체 직렬화
    private User user;

    @JsonIgnore
    private Post post;

    @Builder
    public CommentResponse(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }
}
