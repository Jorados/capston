package jorados.capston.service;


import jorados.capston.domain.Comment;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.dto.request.CommentEdit;
import jorados.capston.dto.request.CommentRequest;
import jorados.capston.dto.response.CommentResponse;
import jorados.capston.dto.response.PostResponse;
import jorados.capston.exception.CommentNotFound;
import jorados.capston.exception.UserNotFound;
import jorados.capston.repository.CommentRepository;
import jorados.capston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    //댓글 생성
    @Transactional
    public void createComment(CommentRequest commentRequest,User user,Post post){
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);
    }

    //댓글 조회
    public CommentResponse readComment(Long commentId){
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound());

        CommentResponse commentResponse = CommentResponse.builder()
                .content(findComment.getContent())
                .user(findComment.getUser())
                .post(findComment.getPost())
                .build();
        return commentResponse;
    }

    //모든 댓글 조회
    public Page<CommentResponse> readAll(Pageable pageable){
        Page<Comment> comments = commentRepository.findAll(pageable);

        return comments.map(comment -> {
            CommentResponse commentResponse = CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createdAt(comment.getFormattedCreatedAt())
                    .user(comment.getUser())
                    .post(comment.getPost())
                    .build();
            return commentResponse;
        });
    }

    // 특정 글에 대한 댓글 조회
    public List<CommentResponse> readPostComment(Long postId){
        List<Comment> comments = commentRepository.findByCommentPostId(postId);
        return comments.stream()
                .map(comment -> {
                    CommentResponse commentResponse = CommentResponse.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getFormattedCreatedAt())
                            .post(comment.getPost())
                            .user(comment.getUser())
                            .build();
                    return commentResponse;
                })
                .collect(Collectors.toList());
    }

    //댓글 수정
    @Transactional
    public void updateComment(Long commentId, CommentEdit commentEdit) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound());
        findComment.edit(
                commentEdit.getContent() != null ? commentEdit.getContent() : findComment.getContent()
        );
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId){
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound());
        commentRepository.delete(findComment);
    }

    // 유저 정보 일치하는지 확인
    public boolean isUserMatch(Long commentId, Long userId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound());
        return findComment.getUser().getId().equals(userId);
    }
}

