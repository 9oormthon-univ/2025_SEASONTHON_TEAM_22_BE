package goormthonuniv.team_22_be.emotion.presentation.docs;


import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Emotion Record", description = "감정 기록 관련 API")
public interface EmotionRecordApiDocs {

    @Operation(
            summary = "감정 기록 등록",
            description = "새로운 감정 기록을 등록합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "감정 기록 등록 성공",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Long>> createEmotionRecord(Long memberId, CreateEmotionRecordRequest request);

    @Operation(
            summary = "감정 기록 목록 조회",
            description = "페이지네이션으로 감정 기록 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = PageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CustomException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<PageResponse<EmotionRecordResponse>>> getEmotionRecords(Pageable pageable);
}
