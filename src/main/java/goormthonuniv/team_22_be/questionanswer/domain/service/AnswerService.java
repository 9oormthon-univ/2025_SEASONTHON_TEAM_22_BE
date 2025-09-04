package goormthonuniv.team_22_be.questionanswer.domain.service;

import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;

public interface AnswerService {

    Long createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request);

    Long getDailyProgress(Long memberId);

    ProgressStatusResponse getProgressStatus(Long memberId);
}
