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
import org.springframework.data.domain.PageImpl;
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
        Page<Object[]> postWithCommentCount;

        if (pageable == null) {
            postWithCommentCount = postRepository.findAllPostsWithCommentCount(Pageable.unpaged());
        } else {
            postWithCommentCount = postRepository.findAllPostsWithCommentCount(pageable);
        }

        List<PostResponse> postResponses = postWithCommentCount.getContent().stream().map(data -> {
            Post post = (Post) data[0];
            long commentCount = (long) data[1];

            PostResponse postResponse = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt().toString())
                    .user(post.getUser())
                    .commentSize(commentCount)  // 댓글 개수 추가
                    .build();
            return postResponse;
        }).collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, postWithCommentCount.getTotalElements());
    }

    // 특정 글 읽기
    public PostResponse readPost(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        PostResponse postResponse = PostResponse.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .user(findPost.getUser())
                .build();

        return postResponse;
    }

    // 글 수정
    @Transactional
    public void updatePost(Long postId,PostEdit postEdit) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        findPost.edit(
                postEdit.getTitle() != null ? postEdit.getTitle() : findPost.getTitle(),
                postEdit.getContent() != null ? postEdit.getContent() : findPost.getContent()
        );
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
