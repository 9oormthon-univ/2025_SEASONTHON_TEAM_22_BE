package goormthonuniv.team_22_be.questioncard.presentation.docs;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.questioncard.application.dto.CreateAnswerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
