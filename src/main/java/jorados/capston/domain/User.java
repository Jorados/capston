package jorados.capston.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jorados.capston.domain.type.UserEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    private int point; // 포인트

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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

//    private String provider;
//    private String providerId;

    @Builder
    public User(Long id, String username, String password, String email,String nickname, UserEnum role,int point) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.point = point;
    }

    public void edit(String username,String password, String email,String nickname){
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public void nickNameUpdate(String nickname){
        this.nickname = nickname;
    }
    public void priceUpdate(int point){
        this.point = point;
    }

}
