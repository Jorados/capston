package jorados.capston.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jorados.capston.domain.type.UserEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id; // DB 넘버

    //@Column(unique = true,nullable = false,length=20)
    private String username; // 시큐리티 로그인 ID

    @Column(nullable = false ,length=60)
    private String password; // 암호

    //@Column(nullable = false,length = 20)
    private String email; // 이메일

    //@Column(nullable = false,length = 20)
    private String nickname; // 닉네임

    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private UserEnum role;  // 권한

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Center> center = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<>();

//    private String provider;
//    private String providerId;


    @Builder
    public User(Long id, String username, String password, String email,String nickname, UserEnum role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public void edit(String username,String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
