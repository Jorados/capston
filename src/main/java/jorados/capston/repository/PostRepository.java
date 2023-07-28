package jorados.capston.repository;

import jorados.capston.domain.Center;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findByTitle(String title);
    Page<Post> findAll(Pageable pageable);

    // 댓글 수와 함께 리턴 ( [ Post , COUNT(댓글) ] )
    @Query("SELECT p, COUNT(c) as commentCount FROM Post p LEFT JOIN p.comment c GROUP BY p")
    Page<Object[]> findAllPostsWithCommentCount(Pageable pageable);

    // 내가 쓴 댓글의 글 조회
    @Query("SELECT DISTINCT c.post  FROM Comment c WHERE c.user.id = :userId")
    Page<Post> findPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    // 내가 쓴 글 조회
    Page<Post> findByUser(User user, Pageable pageable);

    // 제목에 특정 키워드가 포함된 게시글을 검색
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    // 내용에 특정 키워드가 포함된 게시글을 검색
    Page<Post> findByContentContaining(String keyword, Pageable pageable);
}

