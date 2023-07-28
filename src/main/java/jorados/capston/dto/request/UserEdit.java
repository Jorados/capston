package jorados.capston.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserEdit {

    public String username;
    private String password;
    private String email;
    private String nickname;

    @Builder
    public UserEdit(String username,String nickname, String password, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}
