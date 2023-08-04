package jorados.capston.controller;


import jorados.capston.config.auth.PrincipalDetails;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.dto.request.PostEdit;
import jorados.capston.dto.request.PostRequest;
import jorados.capston.dto.response.CommentResponse;
import jorados.capston.dto.response.PostResponse;
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
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        postService.createPost(postRequest,findUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("글이 생성 되었습니다.");
    }

    // 모든 글 읽기 - 오래된 순
    @GetMapping("/readAll")
    public ResponseEntity<?> readAllPost(String keyword,Pageable pageable){
        if (keyword == null) keyword = "latest";
        Page<PostResponse> findAllPosts = postService.readAll(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(findAllPosts);
    }


    // 내가 쓴 글 조회
    @GetMapping("/myPosts")
    public ResponseEntity<?> readAllMyPost(Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        Page<PostResponse> posts = postService.MyPost(findUser, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    // 내가 쓴 댓글의 글 조회
    @GetMapping("/postByMyComments")
    public ResponseEntity<?> readAllMyComment(Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        Page<PostResponse> posts = postService.MyPostsByComment(findUser, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    // 특정 글 읽기
    @GetMapping("/read/{postId}")
    public ResponseEntity<?> findPost(@PathVariable Long postId){
        PostResponse findPost = postService.readPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(findPost);
    }

    // 글 수정
    @PatchMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,@RequestBody PostEdit postEdit,@AuthenticationPrincipal PrincipalDetails principalDetails){

        User findUser = principalDetails.getUser();
        boolean isUserMatch = postService.isUserMatch(postId, findUser.getId());

        if (!isUserMatch) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("글 작성자와 회원 정보가 일치하지 않습니다.");
        }
        else{
            postService.updatePost(postId,postEdit);
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

    // 게시글 검색 api -> title 검색 / content 검색 /
    @GetMapping("/search")
    public ResponseEntity<?> searchPost(@RequestParam String keyword, @RequestParam String searchType,@RequestParam String sortType, Pageable pageable){
        Page<PostResponse> posts = postService.searchPostsByKeyword(keyword, searchType, sortType, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}
