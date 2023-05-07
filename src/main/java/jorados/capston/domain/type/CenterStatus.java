package jorados.capston.domain.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CenterStatus {
    POSSIBLE("예약가능") ,IMPOSSIBLE("예약불가능");
    private String value;
}
