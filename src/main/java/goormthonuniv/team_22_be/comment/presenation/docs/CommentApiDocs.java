package goormthonuniv.team_22_be.comment.presenation.docs;

import goormthonuniv.team_22_be.comment.application.dto.CreateCommentRequest;
import goormthonuniv.team_22_be.comment.application.dto.CommentResponseDto;
import goormthonuniv.team_22_be.comment.application.dto.UpdateCommentRequest;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 API")
public interface CommentApiDocs {

    @Operation(
            summary = "댓글 목록",
            description = "특정 게시글의 댓글을 페이지네이션으로 조회합니다."
    )
    ResponseEntity<ApiResult<PageResponse<CommentResponseDto>>> getCommentsPage(Long postId, Pageable pageable);

    @Operation(
            summary = "내 댓글 목록",
            description = "로그인한 사용자가 작성한 댓글 목록을 페이징으로 조회합니다."
    )
    ResponseEntity<ApiResult<PageResponse<CommentResponseDto>>> getMyComments(Long memberId, Pageable pageable);

    @Operation(
            summary = "댓글 작성",
            description = "특정 게시글에 댓글을 작성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "작성 성공",
                            content = @Content(schema = @Schema(implementation = CommentResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<CommentResponseDto>> createComment(Long memberId, Long postId, CreateCommentRequest request);

    @Operation(
            summary = "댓글 수정",
            description = "본인 댓글만 수정 가능합니다."
    )
    ResponseEntity<ApiResult<CommentResponseDto>> updateComment(Long memberId, Long commentId, UpdateCommentRequest request);

    @Operation(
            summary = "댓글 삭제",
            description = "본인 댓글만 삭제 가능합니다."
    )
    ResponseEntity<ApiResult<Void>> deleteComment(Long memberId, Long commentId);
}