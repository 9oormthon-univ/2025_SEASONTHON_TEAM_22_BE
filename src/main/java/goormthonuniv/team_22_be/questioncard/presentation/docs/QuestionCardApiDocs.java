package goormthonuniv.team_22_be.questioncard.presentation.docs;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.questioncard.application.dto.QuestionCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Question Card", description = "질문 카드 관련 API")
public interface QuestionCardApiDocs {

    @Operation(
            summary = "다음 질문 카드 조회",
            description = "현재 질문 카드 ID를 기준으로 다음 질문 카드를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = QuestionCardResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "질문 카드 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<QuestionCardResponse>> getNextCard(Long questionCardId);

    @Operation(
            summary = "이전 질문 카드 조회",
            description = "현재 질문 카드 ID를 기준으로 이전 질문 카드를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = QuestionCardResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "질문 카드 없음",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<QuestionCardResponse>> getPreviousCard(Long questionCardId);
}
