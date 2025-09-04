package goormthonuniv.team_22_be.questionanswer.infrastructure;

import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.questionanswer.domain.repository.AnswerRepositoryCustom;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static goormthonuniv.team_22_be.questionanswer.domain.model.QAnswer.answer;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByMemberAndQuestionCardId(Member member, QuestionCard questionCard, LocalDateTime startOfDay) {
        Integer count = jpaQueryFactory
                .selectOne()
                .from(answer)
                .where(
                        answer.member.eq(member),
                        answer.questionCard.eq(questionCard),
                        answer.createdAt.goe(startOfDay)
                )
                .fetchFirst();

        return count != null;
    }

    @Override
    public Long countAnswersByMemberForToday(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        Long count = jpaQueryFactory
                .select(answer.count())
                .from(answer)
                .where(
                        answer.member.eq(member),
                        answer.createdAt.goe(startOfDay),
                        answer.createdAt.lt(endOfDay)
                )
                .fetchOne();

        return count != null ? count : 0;
    }

    @Override
    public List<Long> getDailyAnswerCounts(Long memberId) {
        DateTemplate<LocalDate> date = Expressions.dateTemplate(LocalDate.class, "DATE({0})", answer.createdAt);

        return jpaQueryFactory
                .select(answer.id.count())
                .from(answer)
                .where(answer.member.id.eq(memberId))
                .groupBy(date)
                .fetch();

        List.of()
    }

    @Override
    public Long getCompletedAnswersForToday(Long memberId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        Long completedAnswers = jpaQueryFactory
                .select(answer.id.count())
                .from(answer)
                .where(
                        answer.member.id.eq(memberId),
                        answer.createdAt.goe(startOfDay),
                        answer.createdAt.lt(endOfDay)
                )
                .fetchOne();

        return completedAnswers != null ? completedAnswers : 0L;
    }
}
