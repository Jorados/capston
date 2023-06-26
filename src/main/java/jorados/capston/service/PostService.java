package jorados.capston.service;


import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.dto.request.PostEdit;
import jorados.capston.dto.request.PostRequest;
import jorados.capston.dto.response.PostResponse;
import jorados.capston.exception.PostNotFound;
import jorados.capston.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 글 생성
    @Transactional
    public void createPost(PostRequest post, User user){

        Post savePost = Post.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .user(user)
                        .build();

        postRepository.save(savePost);
    }

    // 모든 글 읽기
    public Page<PostResponse> readAll(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> {
            PostResponse postResponse = PostResponse.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .user(post.getUser())
                    .build();
            return postResponse;
        });
    }

    // 특정 글 읽기
    public PostResponse readPost(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        PostResponse postResponse = PostResponse.builder()
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .user(findPost.getUser())
                .build();

        return postResponse;
    }

    // 글 수정
    @Transactional
    public void updatePost(Long postId,PostEdit postEdit,User user) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        if(findPost.getUser() == user){
            findPost.edit(
                    postEdit.getTitle() != null ? postEdit.getTitle() : findPost.getTitle(),
                    postEdit.getContent() != null ? postEdit.getContent() : findPost.getContent()
            );
        }
    }

    // 글 삭제
    @Transactional
    public void deletePost(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        postRepository.delete(findPost);
    }

    public boolean isUserMatch(Long postId, Long userId) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        return findPost.getUser().getId().equals(userId);
    }
}
