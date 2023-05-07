package jorados.capston.domain;


import jorados.capston.domain.type.UserEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id; // DB 넘버

    @Column(unique = true,nullable = false,length=20)
    private String username; // 시큐리티 로그인 ID

    @Column(nullable = false ,length=60)
    private String password; // 암호

    //@Column(nullable = false,length = 20)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private UserEnum role;  // 권한

    @LastModifiedDate
    //@Column(nullable = false)
    private LocalDateTime updateAt; //회원 정보 수정일자

    @CreatedDate
    //@Column(nullable = false)
    private LocalDateTime createdAt; //회원 생성일자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center; // 1인 1시설 예약 가능 ( 다대일 )

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
