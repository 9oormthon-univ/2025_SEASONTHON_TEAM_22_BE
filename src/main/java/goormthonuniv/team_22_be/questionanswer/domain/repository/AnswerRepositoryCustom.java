package goormthonuniv.team_22_be.questionanswer.domain.repository;

import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerRepositoryCustom {

    boolean existsByMemberAndQuestionCardId(Member member, QuestionCard questionCard, LocalDateTime startOfDay);

    Long countAnswersByMemberForToday(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Long> getDailyAnswerCounts(Long memberId);

    Long getCompletedAnswersForToday(Long memberId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
