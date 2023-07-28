package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.Comment;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.dto.request.CommentEdit;
import jorados.capston.dto.request.CommentRequest;
import jorados.capston.dto.response.CommentResponse;
import jorados.capston.dto.response.PostResponse;
import jorados.capston.exception.CommentNotFound;
import jorados.capston.exception.PostNotFound;
import jorados.capston.repository.CommentRepository;
import jorados.capston.repository.PostRepository;
import jorados.capston.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    //특정 글에 댓글 생성
    @PostMapping("/create/{postId}")
    public ResponseEntity<?> commentCreate(@RequestBody CommentRequest commentRequest, @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long postId){
        User findUser = principalDetails.getUser();
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        commentService.createComment(commentRequest,findUser,findPost);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 생성 되었습니다.");
    }

    //모든 댓글 조회
    @GetMapping("/readAll")
    public ResponseEntity<?> commentReadAll(Pageable pageable){
        Page<CommentResponse> findAllComments = commentService.readAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(findAllComments);
    }

    // 특정 글에 대한 댓글 조회
    @GetMapping("/readAll/{postId}")
    public ResponseEntity<?> readAll(@PathVariable Long postId){
        List<CommentResponse> comments = commentService.readPostComment(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }


    //댓글 수정
    @PatchMapping("/update/{commentId}")
    public ResponseEntity<?> commentUpdate(@PathVariable Long commentId,@RequestBody CommentEdit commentEdit,@AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();

        boolean isUserMatch = commentService.isUserMatch(commentId, findUser.getId());
        if (!isUserMatch) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 작성자와 회원 정보가 일치하지 않습니다.");
        }
        else{
            commentService.updateComment(commentId,commentEdit);
            return ResponseEntity.status(HttpStatus.OK).body("댓글이 수정 되었습니다.");
        }
    }

    //댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable Long commentId,@AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();

        boolean isUserMatch = commentService.isUserMatch(commentId, findUser.getId());
        if (!isUserMatch) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 작성자와 회원 정보가 일치하지 않습니다.");
        }
        else{
            commentService.deleteComment(commentId);
            return ResponseEntity.status(HttpStatus.OK).body("댓글이 삭제 되었습니다.");
        }
    }
}
