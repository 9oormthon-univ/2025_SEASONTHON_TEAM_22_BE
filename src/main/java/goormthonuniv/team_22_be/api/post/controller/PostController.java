package goormthonuniv.team_22_be.api.post.controller;

import goormthonuniv.team_22_be.api.post.dto.PostCreateDto;
import goormthonuniv.team_22_be.api.post.dto.PostResponseDto;
import goormthonuniv.team_22_be.api.post.dto.PostUpdateDto;
import goormthonuniv.team_22_be.api.post.presentation.docs.PostApiDocs;
import goormthonuniv.team_22_be.api.post.service.PostService;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController implements PostApiDocs {

    private final PostService postService;

    @Override
    public ResponseEntity<ApiResult<PageResponse<PostResponseDto>>> list(
            String category, Long activityId, Long memberId, @ParameterObject Pageable pageable) {
        var page = postService.list(pageable, category, activityId, memberId);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @Override
    public ResponseEntity<ApiResult<PostResponseDto>> get(Long id) {
        return ResponseEntity.ok(ApiResult.ok(postService.get(id)));
    }

    @Override
    public ResponseEntity<ApiResult<PostResponseDto>> create(@Valid @RequestBody PostCreateDto dto) {
        var created = postService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(created));
    }

    @Override
    public ResponseEntity<ApiResult<PostResponseDto>> update(Long id, @Valid @RequestBody PostUpdateDto dto) {
        var updated = postService.update(id, dto);
        return ResponseEntity.ok(ApiResult.ok(updated));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> delete(Long id) {
        postService.delete(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> like(Long id) {
        postService.like(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> unlike(Long id) {
        postService.unlike(id);
        return ResponseEntity.ok(ApiResult.ok());
    }
}