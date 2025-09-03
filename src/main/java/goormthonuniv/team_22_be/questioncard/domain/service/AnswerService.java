package goormthonuniv.team_22_be.questioncard.domain.service;

import goormthonuniv.team_22_be.questioncard.application.dto.CreateAnswerRequest;

public interface AnswerService {

    Long createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request);
}
