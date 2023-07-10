package jorados.capston.repository;

import jorados.capston.domain.Center;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findByTitle(String title);
    Page<Post> findAll(Pageable pageable);
}
