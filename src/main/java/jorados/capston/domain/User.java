package jorados.capston.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false,length=20)
    private String username;

    @Column(nullable = false ,length=60)
    private String password;

    //@Column(nullable = false,length = 20)
    private String email;

    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private UserEnum role;

    @LastModifiedDate
    //@Column(nullable = false)
    private LocalDateTime updateAt;

    @CreatedDate
    //@Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(Long id, String username, String password, String email, UserEnum role, LocalDateTime updateAt, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
    }

    public void edit(String username,String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
