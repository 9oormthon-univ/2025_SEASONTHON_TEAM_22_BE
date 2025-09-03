package goormthonuniv.team_22_be.activity.presentation.docs;

import goormthonuniv.team_22_be.activity.application.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.activity.application.dto.ActivityResponseDto;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Activity", description = "활동 조회/상세/생성/수정/삭제 및 신청/찜 API")
@RequestMapping("/api/v1/activities")
public interface ActivityApiDocs {

    @Operation(
            summary = "활동 목록 조회",
            description = "페이지네이션으로 활동 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = ApiResult.class)))
            }
    )
    @GetMapping
    ResponseEntity<ApiResult<?>> list(@ParameterObject Pageable pageable);

    @Operation(
            summary = "활동 상세 조회",
            description = "활동 단건 상세를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = ActivityResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "존재하지 않는 활동",
                            content = @Content(schema = @Schema(implementation = CustomException.class)))
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResult<ActivityResponseDto>> get(@PathVariable Long id);

    @Operation(
            summary = "활동 생성",
            description = "새로운 활동을 생성합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")},
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "생성 성공",
                            content = @Content(schema = @Schema(implementation = ActivityResponseDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "유효성 검증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class)))
            }
    )
    @PostMapping
    ResponseEntity<ApiResult<ActivityResponseDto>> create(@Valid @RequestBody ActivityRequestDto request);

    @Operation(
            summary = "활동 수정",
            description = "기존 활동을 수정합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResult<ActivityResponseDto>> update(@PathVariable Long id,
                                                          @Valid @RequestBody ActivityRequestDto request);

    @Operation(
            summary = "활동 삭제",
            description = "활동을 삭제합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id);

    // ----- 찜 / 찜 해제 -----

    @Operation(
            summary = "활동 찜",
            description = "해당 활동을 찜합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @PostMapping("/{id}/like")
    ResponseEntity<ApiResult<Void>> like(@PathVariable Long id);

    @Operation(
            summary = "활동 찜 해제",
            description = "해당 활동의 찜을 해제합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @DeleteMapping("/{id}/like")
    ResponseEntity<ApiResult<Void>> unlike(@PathVariable Long id);

    // ----- 신청 / 신청 취소 -----

    @Operation(
            summary = "활동 신청",
            description = "해당 활동을 신청합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @PostMapping("/{id}/apply")
    ResponseEntity<ApiResult<Void>> apply(@PathVariable Long id);

    @Operation(
            summary = "활동 신청 취소",
            description = "해당 활동 신청을 취소합니다.",
            security = {@SecurityRequirement(name = "BearerAuth")}
    )
    @DeleteMapping("/{id}/apply")
    ResponseEntity<ApiResult<Void>> cancelApply(@PathVariable Long id);
}