package goormthonuniv.team_22_be.post.presentation.docs;

import goormthonuniv.team_22_be.post.application.dto.PostCreateDto;
import goormthonuniv.team_22_be.post.application.dto.PostResponseDto;
import goormthonuniv.team_22_be.post.application.dto.PostUpdateDto;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Post", description = "게시글 조회/상세/생성/수정/삭제 및 좋아요 API")
@RequestMapping("/api/v1/posts")
public interface PostApiDocs {

    @Operation(
            summary = "게시글 목록 조회",
            description = "카테고리/활동/회원 조건 중 하나로 필터링 가능. 아무 조건 없으면 전체 조회.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PageResponse.class)))
            }
    )
    @GetMapping
    ResponseEntity<ApiResult<PageResponse<PostResponseDto>>> list(
            @Parameter(description = "카테고리(POST/REVIEW)") @RequestParam(required = false) String category,
            @Parameter(description = "활동 ID로 필터") @RequestParam(required = false) Long activityId,
            @Parameter(description = "회원 ID로 필터") @RequestParam(required = false) Long memberId,
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary = "게시글 상세 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class)))
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResult<PostResponseDto>> get(@PathVariable Long id);

    @Operation(
            summary = "게시글 생성",
            description = "REVIEW 카테고리는 activityId, rating이 필수입니다.",
            security = { @SecurityRequirement(name = "BearerAuth") },
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "생성 성공",
                            content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "검증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class)))
            }
    )
    @PostMapping
    ResponseEntity<ApiResult<PostResponseDto>> create(@Valid @RequestBody PostCreateDto dto);

    @Operation(
            summary = "게시글 수정",
            description = "본인 게시글만 수정 가능합니다. REVIEW는 rating 필요.",
            security = { @SecurityRequirement(name = "BearerAuth") }
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResult<PostResponseDto>> update(@PathVariable Long id,
                                                      @Valid @RequestBody PostUpdateDto dto);

    @Operation(
            summary = "게시글 삭제",
            description = "본인 게시글만 삭제 가능합니다.",
            security = { @SecurityRequirement(name = "BearerAuth") }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id);

    @Operation(
            summary = "게시글 좋아요",
            description = "이미 좋아요 상태면 멱등 처리합니다.",
            security = { @SecurityRequirement(name = "BearerAuth") }
    )
    @PostMapping("/{id}/like")
    ResponseEntity<ApiResult<Void>> like(@PathVariable Long id);

    @Operation(
            summary = "게시글 좋아요 취소",
            description = "좋아요가 없으면 멱등 처리합니다.",
            security = { @SecurityRequirement(name = "BearerAuth") }
    )
    @DeleteMapping("/{id}/like")
    ResponseEntity<ApiResult<Void>> unlike(@PathVariable Long id);

    @Operation(
            summary = "내가 좋아요 누른 게시글",
            description = "REVIEW , POST 로 나눠서 처리",
            security = { @SecurityRequirement(name = "BearerAuth") }
    )
    @GetMapping("/me/liked")
    ResponseEntity<ApiResult<PageResponse<PostResponseDto>>> listMyLiked(Pageable pageable);
}