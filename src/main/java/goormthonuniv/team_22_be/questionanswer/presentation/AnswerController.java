package goormthonuniv.team_22_be.questionanswer.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyProgressResponse;
import goormthonuniv.team_22_be.questionanswer.presentation.docs.AnswerApiDocs;
import goormthonuniv.team_22_be.questionanswer.domain.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
public class AnswerController implements AnswerApiDocs {

    private final AnswerService answerService;

    @Override
    @PostMapping("/{memberId}/{questionCardId}")
    public ResponseEntity<ApiResult<Long>> createAnswer(@PathVariable Long memberId, @PathVariable Long questionCardId,
                                                  @RequestBody CreateAnswerRequest request
    ) {
        Long answerId = answerService.createAnswer(memberId, questionCardId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(answerId));
    }

    @Override
    @GetMapping("/{memberId}/daily-progress")
    public ResponseEntity<ApiResult<DailyProgressResponse>> getDailyProgress(@PathVariable Long memberId) {
        Long dailyProgress = answerService.getDailyProgress(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(DailyProgressResponse.from(dailyProgress)));
    }
}
