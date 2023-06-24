package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.dto.request.PostEdit;
import jorados.capston.dto.request.PostRequest;
import jorados.capston.dto.response.PostResponse;
import jorados.capston.exception.PostNotFound;
import jorados.capston.repository.PostRepository;
import jorados.capston.service.PostService;
import jorados.capston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final UserService userService;

    // 글 생성
    @PostMapping("/create")
    public ResponseEntity<?> createPost(PostRequest postRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        postService.createPost(postRequest,findUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("글 생성 되었습니다.");
    }

    // 모든 글 읽기
    @GetMapping("/readAll")
    public ResponseEntity<?> readAllPost(Pageable pageable){
        Page<PostResponse> findAllPosts = postService.readAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(findAllPosts);
    }

    // 특정 글 읽기
    @GetMapping("/read/{postId}")
    public ResponseEntity<?> findPost(@PathVariable Long postId){
        PostResponse findPost = postService.readPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(findPost);
    }

    // 글 수정
    @PutMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,@RequestBody PostEdit postEdit,@AuthenticationPrincipal PrincipalDetails principalDetails){

        User findUser = principalDetails.getUser();
        boolean isUserMatch = postService.isUserMatch(postId, findUser.getId());

        if (!isUserMatch) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("글 작성자와 회원 정보가 일치하지 않습니다.");
        }
        else{
            postService.updatePost(postId,postEdit,findUser);
            return ResponseEntity.status(HttpStatus.OK).body("글 수정이 완료 되었습니다");
        }
    }

    // 글 삭제
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,@AuthenticationPrincipal PrincipalDetails principalDetails){

        User findUser = principalDetails.getUser();
        boolean isUserMatch = postService.isUserMatch(postId, findUser.getId());

        if(!isUserMatch){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("글 작성자와 회원 정보가 일치하지 않습니다.");
        }
        else{
            postService.deletePost(postId);
            return ResponseEntity.status(HttpStatus.OK).body("글 삭제가 완료 되었습니다");
        }
    }

}
