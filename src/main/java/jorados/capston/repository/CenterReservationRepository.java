package jorados.capston.repository;


import jorados.capston.domain.Center;
import jorados.capston.domain.CenterReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CenterReservationRepository extends JpaRepository<CenterReservation,Long> {

    // 예약 리스트 : ReservingDate와 Center 리스트 조회
    List<CenterReservation> findAllByCenterAndReservingDate(Center center, LocalDate reservingDate);
}
