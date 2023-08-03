package jorados.capston.repository;

import jorados.capston.domain.Comment;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAll(Pageable pageable);
    @Query("select c from Comment c left join fetch c.post where c.post.id =:postId")
    List<Comment> findByCommentPostId(@Param("postId") Long postId);

    // 내가 쓴 댓글 조회
    Page<Comment> findByUser(User user, Pageable pageable);


}
