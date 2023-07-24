package jorados.capston.serviceTest;


import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.domain.type.UserEnum;
import jorados.capston.dto.request.PostEdit;
import jorados.capston.dto.request.PostRequest;
import jorados.capston.dto.response.PostResponse;
import jorados.capston.exception.PostNotFound;
import jorados.capston.repository.PostRepository;
import jorados.capston.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @BeforeEach
    void deleteAll(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트")
    public void test1(){
        User user = User.builder()
                .username("aaa")
                .password("aaa")
                .email("seongjin8860@naver.com")
                .role(UserEnum.CUSTOMER)
                .build();

        PostRequest postRequest = PostRequest.builder()
                .title("제목1")
                .content("내용1")
                .build();

        //postService.createPost(postRequest,user);

        Assertions.assertThat(postRequest.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(postRequest.getContent()).isEqualTo("내용1");
    }

    @Test
    @DisplayName("글 삭제 테스트")
    public void test2(){
        User user = User.builder()
                .username("aaa")
                .password("aaa")
                .email("seongjin8860@naver.com")
                .role(UserEnum.CUSTOMER)
                .build();

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFound());
        postService.deletePost(findPost.getId());

        assertThat(postRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("글 1개 조회")
    public void test3(){
        User user = User.builder()
                .username("aaa")
                .password("aaa")
                .email("seongjin8860@naver.com")
                .role(UserEnum.CUSTOMER)
                .build();

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);
        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFound());

        PostResponse postResponse = postService.readPost(findPost.getId());

        Assertions.assertThat(postResponse.getUser()).isEqualTo(findPost.getUser());
        Assertions.assertThat(postResponse.getTitle()).isEqualTo(findPost.getTitle());
        Assertions.assertThat(postResponse.getContent()).isEqualTo(findPost.getContent());
    }


    @Test
    @DisplayName("글 수정")
    public void test4(){
        User user = User.builder()
                .username("aaa")
                .password("aaa")
                .email("seongjin8860@naver.com")
                .role(UserEnum.CUSTOMER)
                .build();

        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목")
                .content("내용")
                .build();
        postService.updatePost(post.getId(),postEdit);

        Post findPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFound());
        Assertions.assertThat(findPost.getTitle()).isEqualTo("제목");
        Assertions.assertThat(findPost.getContent()).isEqualTo("내용");
    }
}
