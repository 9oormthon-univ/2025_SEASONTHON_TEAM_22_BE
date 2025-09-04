package goormthonuniv.team_22_be.questionanswer.domain.service;

import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;

public interface AnswerService {

    Long createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request);
    Long getDailyProgress(Long memberId);
}
