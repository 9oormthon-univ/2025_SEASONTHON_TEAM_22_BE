package goormthonuniv.team_22_be.activity.presentation;

import goormthonuniv.team_22_be.activity.application.service.ActivityService;
import goormthonuniv.team_22_be.activity.application.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.activity.application.dto.ActivityResponseDto;
import goormthonuniv.team_22_be.activity.presentation.docs.ActivityApiDocs;
import goormthonuniv.team_22_be.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class ActivityController implements ActivityApiDocs {

    private final ActivityService activityService;

    @Override
    public ResponseEntity<ApiResult<?>> list(@PageableDefault(size = 10) Pageable pageable) {
        var page = activityService.list(pageable);
        return ResponseEntity.ok(ApiResult.ok(page));
    }

    @Override
    public ResponseEntity<ApiResult<ActivityResponseDto>> get(Long id) {
        var dto = activityService.get(id);
        return ResponseEntity.ok(ApiResult.ok(dto));
    }

    @Override
    public ResponseEntity<ApiResult<ActivityResponseDto>> create(ActivityRequestDto request) {
        var dto = activityService.create(request);
        return ResponseEntity.status(201).body(ApiResult.ok(dto));
    }

    @Override
    public ResponseEntity<ApiResult<ActivityResponseDto>> update(Long id, ActivityRequestDto request) {
        var dto = activityService.update(id, request);
        return ResponseEntity.ok(ApiResult.ok(dto));
    }

    @Override
    public ResponseEntity<ApiResult<Void>> delete(Long id) {
        activityService.delete(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> like(Long id) {
        activityService.like(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> unlike(Long id) {
        activityService.unlike(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> apply(Long id) {
        activityService.apply(id);
        return ResponseEntity.ok(ApiResult.ok());
    }

    @Override
    public ResponseEntity<ApiResult<Void>> cancelApply(Long id) {
        activityService.cancelApply(id);
        return ResponseEntity.ok(ApiResult.ok());
    }
}