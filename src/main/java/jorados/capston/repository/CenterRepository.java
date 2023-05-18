package jorados.capston.repository;


import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center,Long> {
    public Optional<Center> findByCenter_name(String center_name);
    public Optional<Center> findById(Long id);
}
