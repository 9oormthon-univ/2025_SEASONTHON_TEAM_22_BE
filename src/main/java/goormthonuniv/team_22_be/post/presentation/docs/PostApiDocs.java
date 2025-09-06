package goormthonuniv.team_22_be.post.presentation.docs;

import goormthonuniv.team_22_be.post.application.dto.CreatePostRequest;
import goormthonuniv.team_22_be.post.application.dto.PostResponse;
import goormthonuniv.team_22_be.post.application.dto.UpdatePostRequest;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 조회/상세/생성/수정/삭제 및 좋아요 API")
public interface PostApiDocs {

    @Operation(
            summary = "게시글 목록 조회",
            description = "카테고리/활동/회원 조건 중 하나로 필터링 가능. 아무 조건 없으면 전체 조회.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PageResponse.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PageResponse<PostResponse>>> getPostPage(
            @Parameter(description = "카테고리(POST/REVIEW)") String category,
            @Parameter(description = "회원 ID로 필터") Long memberId,
            @Parameter(description = "활동 ID로 필터") Long activityId,
            Pageable pageable
    );

    @Operation(
            summary = "게시글 상세 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PostResponse.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PostResponse>> getPost(Long postId);

    @Operation(
            summary = "게시글 생성",
            description = "REVIEW 카테고리는 activityId, rating이 필수입니다.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "생성 성공",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "검증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Long>> createPost(Long memberId, CreatePostRequest dto);

    @Operation(
            summary = "게시글 수정",
            description = "본인 게시글만 수정 가능합니다. REVIEW는 rating 필요.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "수정 성공",
                            content = @Content(schema = @Schema(implementation = PostResponse.class))
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "검증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "권한 없음(본인 글 아님)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PostResponse>> updatePost(Long memberId, Long postId, UpdatePostRequest request);

    @Operation(
            summary = "게시글 삭제",
            description = "본인 게시글만 삭제 가능합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "삭제 성공",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "권한 없음(본인 글 아님)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Void>> deletePost(Long memberId ,Long postId);

    @Operation(
            summary = "게시글 좋아요",
            description = "이미 좋아요 상태면 멱등 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "좋아요 성공",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Void>> like(Long memberId, Long postId);

    @Operation(
            summary = "게시글 좋아요 취소",
            description = "좋아요가 없으면 멱등 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "좋아요 취소 성공",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Void>> unlike(Long memberId, Long postId);

    @Operation(
            summary = "내가 좋아요 누른 게시글",
            description = "REVIEW , POST 로 나눠서 처리",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PostResponse.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PageResponse<PostResponse>>> getMyLiked(Long memberId, Pageable pageable);


    @Operation(
            summary = "내가 쓴 게시글 조회",
            description = "category 파라미터로 필터링 가능(예: REVIEW). 없으면 전체 반환.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PageResponse.class))
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "잘못된 카테고리",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PageResponse<PostResponse>>> getMyPosts(Long memberId, Pageable pageable,
            @Parameter(description = "카테고리(예: REVIEW). 미지정 시 전체") String category
    );

    @Operation(
            summary = "내가 쓴 후기만 조회",
            description = "카테고리 REVIEW에 해당하는 게시글만 반환.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    ResponseEntity<ApiResult<PageResponse<PostResponse>>> getMyReviews(Long memberId, Pageable pageable);

}