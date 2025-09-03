package goormthonuniv.team_22_be.questioncard.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.questioncard.application.dto.QuestionCardResponse;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questioncard.domain.service.QuestionCardService;
import goormthonuniv.team_22_be.questioncard.presentation.docs.QuestionCardApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question-cards")
@RequiredArgsConstructor
public class QuestionCardController implements QuestionCardApiDocs {

    private final QuestionCardService questionCardService;

    @GetMapping("/{questionCardId}/next")
    @Override
    public ResponseEntity<ApiResult<QuestionCardResponse>> getNextCard(@PathVariable Long questionCardId) {
        QuestionCard nextCard = questionCardService.getNextCard(questionCardId);
        QuestionCardResponse response = QuestionCardResponse.from(nextCard);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(response));
    }

    @GetMapping("/{questionCardId}/previous")
    @Override
    public ResponseEntity<ApiResult<QuestionCardResponse>> getPreviousCard(@PathVariable Long questionCardId) {
        QuestionCard previousCard = questionCardService.getPreviousCard(questionCardId);
        QuestionCardResponse response = QuestionCardResponse.from(previousCard);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(response));
    }
}
