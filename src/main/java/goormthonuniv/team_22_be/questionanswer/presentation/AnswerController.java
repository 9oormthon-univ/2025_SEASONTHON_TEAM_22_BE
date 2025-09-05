package goormthonuniv.team_22_be.questionanswer.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.questionanswer.application.dto.AnswerResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyAnswerRecordResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyProgressResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;
import goormthonuniv.team_22_be.questionanswer.domain.service.AnswerService;
import goormthonuniv.team_22_be.questionanswer.presentation.docs.AnswerApiDocs;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
public class AnswerController implements AnswerApiDocs {

    private final AnswerService answerService;

    @PostMapping("/{memberId}/{questionCardId}")
    @Override
    public ResponseEntity<ApiResult<Long>> createAnswer(@PathVariable Long memberId, @PathVariable Long questionCardId,
                                                        @RequestBody CreateAnswerRequest request
    ) {
        Long answerId = answerService.createAnswer(memberId, questionCardId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(answerId));
    }

    @GetMapping("/{memberId}/daily-progress")
    @Override
    public ResponseEntity<ApiResult<DailyProgressResponse>> getDailyProgress(@PathVariable Long memberId) {
        Long dailyProgress = answerService.getDailyProgress(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(DailyProgressResponse.from(dailyProgress)));
    }

    @GetMapping("/{memberId}/progress-status")
    @Override
    public ResponseEntity<ApiResult<ProgressStatusResponse>> getProgressStatus(@PathVariable Long memberId) {
        ProgressStatusResponse response = answerService.getProgressStatus(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(response));
    }

    @GetMapping("/{memberId}/history")
    @Override
    public ResponseEntity<ApiResult<PageResponse<DailyAnswerRecordResponse>>> getDailyRecords(
            @PathVariable Long memberId,
            @PageableDefault(page = 0, size = 6) Pageable pageable
    ) {
        Page<DailyAnswerRecordResponse> dailyAnswerRecords = answerService.getDailyAnswerRecords(memberId, pageable);
        return ResponseEntity.ok(ApiResult.ok(PageResponse.of(dailyAnswerRecords)));
    }

    @GetMapping("/{memberId}/{date}")
    @Override
    public ResponseEntity<ApiResult<List<AnswerResponse>>> getAnswersByDate(@PathVariable Long memberId, @PathVariable LocalDate date) {
        List<AnswerResponse> answers = answerService.getAnswersByDate(memberId, date);
        return ResponseEntity.ok(ApiResult.ok(answers));
    }
}
