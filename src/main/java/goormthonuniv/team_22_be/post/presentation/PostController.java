package goormthonuniv.team_22_be.post.presentation;

import goormthonuniv.team_22_be.post.application.dto.CreatePostRequest;
import goormthonuniv.team_22_be.post.application.dto.PostResponse;
import goormthonuniv.team_22_be.post.application.dto.UpdatePostRequest;
import goormthonuniv.team_22_be.post.presentation.docs.PostApiDocs;
import goormthonuniv.team_22_be.post.domain.service.PostService;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController implements PostApiDocs {

    private final PostService postService;

    @GetMapping("/{activityId}/{memberId}")
    @Override
    public ResponseEntity<ApiResult<PageResponse<PostResponse>>> getPostPage(
            @RequestParam(required = false) String category, @PathVariable Long memberId, @PathVariable Long activityId,
            @PageableDefault Pageable pageable) {
        var page = postService.getPostPage(pageable, category, activityId, memberId);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @GetMapping("/{postId}")
    @Override
    public ResponseEntity<ApiResult<PostResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResult.ok(postService.getPost(postId)));
    }

    @PostMapping("/{memberId}")
    @Override
    public ResponseEntity<ApiResult<Long>> createPost(@PathVariable Long memberId, @Valid @RequestBody CreatePostRequest request) {
        Long postId = postService.createPost(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(postId));
    }

    @PutMapping("/{postId}/{memberId}")
    @Override
    public ResponseEntity<ApiResult<PostResponse>> updatePost(@PathVariable Long memberId, @PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {
        var updated = postService.updatePost(memberId, postId, request);
        return ResponseEntity.ok(ApiResult.ok(updated));
    }

    @DeleteMapping("/{postId}/{memberId}")
    @Override
    public ResponseEntity<ApiResult<Void>> deletePost(@PathVariable Long memberId, @PathVariable Long postId) {
        postService.deletePost(memberId, postId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @PostMapping("/{postId}/like/{memberId}")
    @Override
    public ResponseEntity<ApiResult<Void>> like(@PathVariable Long postId, @PathVariable Long memberId) {
        postService.like(postId, memberId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @PostMapping("/{postId}/like/{memberId}")
    @Override
    public ResponseEntity<ApiResult<Void>> unlike(@PathVariable Long memberId, @PathVariable Long postId) {
        postService.unlike(memberId, postId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @GetMapping("/me/{memberId}/liked")
    @Override
    public ResponseEntity<ApiResult<PageResponse<PostResponse>>> getMyLiked(@PathVariable Long memberId,
                                                                             @PageableDefault Pageable pageable) {
        var body = postService.listMyLiked(memberId, pageable);
        return ResponseEntity.ok(ApiResult.ok(body));
    }

    @GetMapping("/{memberId}/me")
    @Override
    public ResponseEntity<ApiResult<PageResponse<PostResponse>>> getMyPosts(@PathVariable Long memberId, @PageableDefault Pageable pageable,
                                                                            @RequestParam(required = false) String category
    ) {
        var body = postService.listMyPosts(memberId, pageable, category);
        return ResponseEntity.ok(ApiResult.ok(body));
    }

    @GetMapping("/{memberId}/reviews")
    @Override
    public ResponseEntity<ApiResult<PageResponse<PostResponse>>> getMyReviews(@PathVariable Long memberId, @PageableDefault Pageable pageable) {
        var body = postService.listMyReviews(memberId, pageable);
        return ResponseEntity.ok(ApiResult.ok(body));
    }


}