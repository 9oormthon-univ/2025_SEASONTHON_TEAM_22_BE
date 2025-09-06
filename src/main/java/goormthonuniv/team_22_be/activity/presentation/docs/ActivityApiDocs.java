package goormthonuniv.team_22_be.activity.presentation.docs;

import goormthonuniv.team_22_be.activity.application.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.activity.application.dto.ActivityResponseDto;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Activity", description = "활동 조회/상세/생성/수정/삭제 및 신청/찜 API")
@RequestMapping("/api/v1/activities")
public interface ActivityApiDocs {

    @Operation(summary = "활동 목록 조회")
    @GetMapping
    ResponseEntity<ApiResult<PageResponse<?>>> list(@ParameterObject Pageable pageable);

    @Operation(summary = "활동 상세 조회")
    @GetMapping("/{id}")
    ResponseEntity<ApiResult<ActivityResponseDto>> get(@PathVariable Long id);

    @Operation(summary = "활동 생성")
    @PostMapping
    ResponseEntity<ApiResult<ActivityResponseDto>> create(@Valid @RequestBody ActivityRequestDto request);

    @Operation(summary = "활동 수정")
    @PutMapping("/{id}")
    ResponseEntity<ApiResult<ActivityResponseDto>> update(@PathVariable Long id,
                                                          @Valid @RequestBody ActivityRequestDto request);

    @Operation(summary = "활동 삭제")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id);

    // ----- 찜 / 찜 해제 -----

    @Operation(summary = "활동 찜")
    @PostMapping("/{id}/like/{memberId}")
    ResponseEntity<ApiResult<Void>> like(@PathVariable Long id, @PathVariable Long memberId);

    @Operation(summary = "활동 찜 해제")
    @DeleteMapping("/{id}/like/{memberId}")
    ResponseEntity<ApiResult<Void>> unlike(@PathVariable Long id, @PathVariable Long memberId);

    @Operation(summary = "내가 찜한 활동 페이징")
    @GetMapping("/{member_id}/liked")
    ResponseEntity<ApiResult<PageResponse<ActivityResponseDto>>> listMyLiked(
            @PathVariable("member_id") Long memberId,
            @ParameterObject Pageable pageable
    );


    // ----- 신청 / 신청 취소 -----

    @Operation(summary = "활동 신청")
    @PostMapping("/{id}/apply/{memberId}")
    ResponseEntity<ApiResult<Void>> apply(@PathVariable Long id, @PathVariable Long memberId);

    @Operation(summary = "활동 신청 취소")
    @DeleteMapping("/{id}/apply/{memberId}")
    ResponseEntity<ApiResult<Void>> cancelApply(@PathVariable Long id, @PathVariable Long memberId);

    @Operation(summary = "내가 신청한 활동(페이징)")
    @GetMapping("/members/{memberId}/applied")
    ResponseEntity<ApiResult<PageResponse<ActivityResponseDto>>> listMyApplied(@PathVariable Long memberId,
                                                                               @ParameterObject Pageable pageable);
}