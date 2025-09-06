package goormthonuniv.team_22_be.activity.presentation;

import goormthonuniv.team_22_be.activity.application.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.activity.application.dto.ActivityResponseDto;
import goormthonuniv.team_22_be.activity.application.service.ActivityService;
import goormthonuniv.team_22_be.activity.presentation.docs.ActivityApiDocs;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActivityController implements ActivityApiDocs {

    private final ActivityService activityService;

    @Override
    public ResponseEntity<ApiResult<PageResponse<?>>> list(@ParameterObject Pageable pageable) {
        var page = activityService.list(pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @Override
    public ResponseEntity<ApiResult<ActivityResponseDto>> get(Long id) {
        var dto = activityService.get(id);
        return ResponseEntity.ok(ApiResult.ok(dto));
    }

    @Override
    public ResponseEntity<ApiResult<ActivityResponseDto>> create(@Valid ActivityRequestDto request) {
        var dto = activityService.create(request);
        return ResponseEntity.status(201).body(ApiResult.ok(dto));
    }

    @Override
    public ResponseEntity<ApiResult<ActivityResponseDto>> update(Long id, @Valid ActivityRequestDto request) {
        var dto = activityService.update(id, request);
        return ResponseEntity.ok(ApiResult.ok(dto));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> delete(Long id) {
        activityService.delete(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> like(Long id, Long memberId) {
        activityService.like(id, memberId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> unlike(Long id, Long memberId) {
        activityService.unlike(id, memberId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> apply(Long id, Long memberId) {
        activityService.apply(id, memberId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> cancelApply(Long id, Long memberId) {
        activityService.cancelApply(id, memberId);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<PageResponse<ActivityResponseDto>>> listMyApplied(Long memberId, Pageable pageable) {
        var page = activityService.listMyApplied(memberId, pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @Override
    public ResponseEntity<ApiResult<PageResponse<ActivityResponseDto>>> listMyLiked(Long memberId, Pageable pageable) {
        var page = activityService.listMyLiked(memberId, pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

}