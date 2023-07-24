package jorados.capston.repository;

import jorados.capston.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username =:username ")
    User findByUsername2(@Param("username") String username);

    List<User> findAllByUsername(String username);

    List<User> findAllByNickname(String nickname);
}
