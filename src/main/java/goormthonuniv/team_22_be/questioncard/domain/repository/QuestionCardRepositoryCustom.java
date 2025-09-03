package goormthonuniv.team_22_be.questioncard.domain.repository;

import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;

import java.util.Optional;

public interface QuestionCardRepositoryCustom {

    Optional<QuestionCard> findNextCard(Long currentId);
    Optional<QuestionCard> findPreviousCard(Long currentId);
}
