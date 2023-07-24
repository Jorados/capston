package jorados.capston.repository;

import jorados.capston.domain.Center;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findByTitle(String title);


    Page<Post> findAll(Pageable pageable);

    // 댓글 수와 함께 리턴 ( [ Post , COUNT(댓글) ] )
    @Query("SELECT p, COUNT(c) as commentCount FROM Post p LEFT JOIN p.comment c GROUP BY p")
    Page<Object[]> findAllPostsWithCommentCount(Pageable pageable);


}

