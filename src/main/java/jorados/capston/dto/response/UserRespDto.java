package jorados.capston.dto.response;

import jorados.capston.domain.User;
import lombok.Getter;
import lombok.Setter;


public class UserRespDto {
    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private String email;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }
}
