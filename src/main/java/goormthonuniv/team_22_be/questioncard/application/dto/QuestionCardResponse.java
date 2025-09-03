package goormthonuniv.team_22_be.questioncard.application.dto;

import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;

public record QuestionCardResponse(

        Long id,
        String cardType,
        String content
) {
    public static QuestionCardResponse from(QuestionCard nextCard) {
        return new QuestionCardResponse(
                nextCard.getId(),
                nextCard.getCardType().getDescription(),
                nextCard.getContent()
        );
    }
}
