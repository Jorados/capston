package jorados.capston.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String username;
    private String password;
    private String email;

    @Builder
    public UserResponse(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
