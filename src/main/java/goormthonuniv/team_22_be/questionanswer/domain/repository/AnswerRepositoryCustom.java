package goormthonuniv.team_22_be.questionanswer.domain.repository;

import com.querydsl.core.Tuple;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerRepositoryCustom {

    boolean existsByMemberAndQuestionCardId(Member member, QuestionCard questionCard, LocalDateTime startOfDay);

    Long countAnswersByMemberForToday(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Long> getDailyAnswerCounts(Long memberId);

    Long getCompletedAnswersForToday(Long memberId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    Page<Tuple> findDailyAnswerRecords(Long memberId, Pageable pageable);
}
