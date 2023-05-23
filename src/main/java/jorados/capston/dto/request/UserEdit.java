package jorados.capston.dto.request;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserEdit {


    @NotBlank
    public String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @Builder
    public UserEdit(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
