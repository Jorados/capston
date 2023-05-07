package jorados.capston.domain;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Center_Img {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="center_img_id")
    private Long id; // DB 넘버

    @Column(name = "img_id")
    private String img_Id; // 이미지 넘버
    @Column(name = "img_url", length = 1000)
    private String img_Url; // 이미지 경로

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center; // 어떠 한 사진은 하나의 시설에만 등록 (다대일)

}
