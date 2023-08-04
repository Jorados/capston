package jorados.capston.service;


import jorados.capston.domain.Comment;
import jorados.capston.domain.Post;
import jorados.capston.domain.User;
import jorados.capston.dto.request.PostEdit;
import jorados.capston.dto.request.PostRequest;
import jorados.capston.dto.response.CommentResponse;
import jorados.capston.dto.response.PostResponse;
import jorados.capston.exception.PostNotFound;
import jorados.capston.exception.UserNotFound;
import jorados.capston.repository.PostRepository;
import jorados.capston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

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

    // 모든 글 읽기 -> keyword에 따라 오름차순, 내림차순 정렬
    public Page<PostResponse> readAll(String keyword,Pageable pageable){
        Page<Post> posts;

        if ("latest".equalsIgnoreCase(keyword)) {
            posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        else if ("oldest".equalsIgnoreCase(keyword)){
            posts = postRepository.findAllByOrderByCreatedAtAsc(pageable);
        }
        else posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<PostResponse> postResponses = posts.getContent().stream().map(post -> {
            PostResponse postResponse = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getFormattedCreatedAt())
                    .user(post.getUser())
                    .commentSize(post.getComment().size())  // 댓글 개수 추가
                    .build();
            return postResponse;
        }).collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, posts.getTotalElements());
    }

    // 특정 글 읽기
    public PostResponse readPost(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());

        PostResponse postResponse = PostResponse.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .user(findPost.getUser())
                .createdAt(findPost.getFormattedCreatedAt())
                .commentSize(findPost.getComment().size())
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

    // 내가 쓴 글 조회
    public Page<PostResponse> MyPost(User user,Pageable pageable){
        Page<Post> myPosts = postRepository.findByUser(user, pageable);

        List<PostResponse> postResponses = myPosts.getContent().stream().map(post -> {
            PostResponse postResponse = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getFormattedCreatedAt())
                    .user(post.getUser())
                    .commentSize(post.getComment().size())
                    .build();
            return postResponse;
        }).collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, myPosts.getTotalElements());
    }

    // 내가 쓴 댓글의 글 조회
    public Page<PostResponse> MyPostsByComment(User user, Pageable pageable){
        Long userId = user.getId();
        Page<Post> myPosts = postRepository.findPostsByUserId(userId, pageable);

        List<PostResponse> postResponses = myPosts.getContent().stream().map(post -> {
            PostResponse postResponse = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getFormattedCreatedAt())
                    .user(post.getUser())
                    .commentSize(post.getComment().size())
                    .build();
            return postResponse;
        }).collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, myPosts.getTotalElements());
    }

    // 검색(title,content) 글 조회 + sortType 에 따라 정렬
    public Page<PostResponse> searchPostsByKeyword(String keyword, String searchType, String sortType, Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt"); // 기본적으로 최신순 정렬
        if ("oldest".equals(sortType)) {
            sort = Sort.by(Sort.Direction.ASC, "createdAt"); // 오래된순 정렬
        }

        Pageable realPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Post> posts;
        if ("title".equals(searchType)) {
            posts = postRepository.findByTitleContaining(keyword, realPageable);
        } else if ("content".equals(searchType)) {
            posts = postRepository.findByContentContaining(keyword, realPageable);
        } else {
            posts = postRepository.findAll(realPageable);
        }

        List<PostResponse> postResponses = posts.getContent().stream().map(post -> {
            PostResponse postResponse = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getFormattedCreatedAt())
                    .commentSize(post.getComment().size())
                    .user(post.getUser())
                    .build();
            return postResponse;
        }).collect(Collectors.toList());

        return new PageImpl<>(postResponses, realPageable, posts.getTotalElements());
    }




    public boolean isUserMatch(Long postId, Long userId) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        return findPost.getUser().getId().equals(userId);
    }
}
