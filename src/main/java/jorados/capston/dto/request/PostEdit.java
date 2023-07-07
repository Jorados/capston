package jorados.capston.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
public class PostEdit {
    private String title;
    private String content;


    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
