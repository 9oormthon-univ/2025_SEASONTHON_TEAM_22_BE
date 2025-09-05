package goormthonuniv.team_22_be.questionanswer.infrastructure;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.questionanswer.domain.repository.AnswerRepositoryCustom;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Tuple> findDailyAnswerRecords(Long memberId, Pageable pageable) {
        DateTemplate<LocalDate> answerDate = Expressions.dateTemplate(
                LocalDate.class,
                "DATE({0})",
                answer.createdAt
        );

        List<Tuple> results = jpaQueryFactory
                .select(answerDate, answer.id.count())
                .from(answer)
                .where(answer.member.id.eq(memberId))
                .groupBy(answerDate)
                .orderBy(answerDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(answerDate.countDistinct())
                .from(answer)
                .where(answer.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(results, pageable, total == null ? 0 : total);
    }
}
