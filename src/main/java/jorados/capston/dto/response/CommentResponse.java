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

    private Long id;
    private String content;
    private String createdAt;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //프록시객체무시하고 원본객체 직렬화
    private User user;

    @JsonIgnore
    private Post post;

    @Builder
    public CommentResponse(Long id, String content,String createdAt, User user, Post post) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }
}
