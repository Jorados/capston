package jorados.capston.dto.response;


import jorados.capston.domain.type.UserEnum;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class UserResponse {

    private Long id;
    private String username;
    private int point;
    private String password;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserEnum role;

    @Builder
    public UserResponse(Long id, String username, String password, String email,String nickname, UserEnum role,int point) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.point = point;
    }
}
