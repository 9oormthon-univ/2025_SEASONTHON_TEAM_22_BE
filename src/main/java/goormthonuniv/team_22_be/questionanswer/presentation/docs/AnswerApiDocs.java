package goormthonuniv.team_22_be.questionanswer.presentation.docs;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyAnswerRecordResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyProgressResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Answer", description = "질문 답변 관련 API")
public interface AnswerApiDocs {

    @Operation(
            summary = "답변 등록",
            description = "새로운 답변을 등록합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "답변 등록 성공",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "질문 카드 또는 멤버를 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Long>> createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request);

    @Operation(
            summary = "일일 답변 진행률 조회",
            description = "사용자의 일일 답변 진행률을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일일 답변 진행률 조회 성공",
                            content = @Content(schema = @Schema(implementation = DailyProgressResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "멤버를 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이미 답변이 존재함",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<DailyProgressResponse>> getDailyProgress(Long memberId);

    @Operation(
            summary = "마음 훈련 기록 현황 조회",
            description = "사용자의 마음 훈련 기록 현황을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마음 훈련 기록 현황 조회 성공",
                            content = @Content(schema = @Schema(implementation = ProgressStatusResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "멤버를 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<ProgressStatusResponse>> getProgressStatus(Long memberId);

    @Operation(
            summary = "마음 훈련 날짜별 기록 현황 조회",
            description = "사용자의 마음 훈련 날짜별 기록 현황을 페이지네이션으로 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마음 훈련 날짜별 기록 현황 조회 성공",
                            content = @Content(schema = @Schema(implementation = DailyAnswerRecordResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "멤버를 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PageResponse<DailyAnswerRecordResponse>>> getDailyRecords(Long memberId, Pageable pageable);
}
