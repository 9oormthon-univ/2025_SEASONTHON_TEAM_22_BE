package goormthonuniv.team_22_be.post.domain.service;

import goormthonuniv.team_22_be.post.application.dto.CreatePostRequest;
import goormthonuniv.team_22_be.post.application.dto.PostResponse;
import goormthonuniv.team_22_be.post.application.dto.UpdatePostRequest;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PageResponse<PostResponse> getPostPage(Pageable pageable, String category, Long activityId, Long memberId);

    PostResponse getPost(Long postId);

    PostResponse updatePost(Long memberId, Long postId, UpdatePostRequest dto);

    Long createPost(Long memberId, CreatePostRequest request);

    void deletePost(Long memberId, Long postId);

    void like(Long memberId, Long postId);

    void unlike(Long memberId, Long postId);

    PageResponse<PostResponse> listMyLiked(Long memberId, Pageable pageable);

    PageResponse<PostResponse> listMyPosts(Long memberId, Pageable pageable, String category);

    PageResponse<PostResponse> listMyReviews(Long memberId, Pageable pageable);
}