package goormthonuniv.team_22_be.questioncard.domain.service;

import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;

public interface QuestionCardService {

    QuestionCard getNextCard(Long currentId);
    QuestionCard getPreviousCard(Long currentId);
}
