package goormthonuniv.team_22_be.questioncard.domain.repository;

import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;

import java.time.LocalDateTime;

public interface AnswerRepositoryCustom {

    boolean existsByMemberAndQuestionCardId(Member member, QuestionCard questionCard);
    Long countAnswersByMemberForToday(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
