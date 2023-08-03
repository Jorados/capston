package jorados.capston.repository;


import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center,Long> {

    // userId로 center 조회
    @Query("select c from Center c inner join c.user u where u.id =:userId ")
    public List<Center> findCenter(@Param("userId") Long userId);

    public Optional<Center> findById(Long id);

    @Query("select c from Center c where c.center_name like %:searchValue% ")
    Page<Center> findAllSearch(String searchValue, Pageable pageable);

}
