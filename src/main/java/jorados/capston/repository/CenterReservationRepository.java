package jorados.capston.repository;


import jorados.capston.domain.Center;
import jorados.capston.domain.CenterReservation;
import jorados.capston.domain.User;
import jorados.capston.domain.type.CenterReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CenterReservationRepository extends JpaRepository<CenterReservation,Long> {

    // 예약 리스트 : ReservingDate와 Center 리스트 조회
    List<CenterReservation> findAllByCenterAndReservingDate(Center center, LocalDate reservingDate);

    // 내 예약목록 최신순 조회
    Page<CenterReservation> findAllByUserOrderByReservingDateDesc(User user, Pageable pageable);

    // 예약 상태 조회
    List<CenterReservation> findByStatus(CenterReservationStatus status);
}
