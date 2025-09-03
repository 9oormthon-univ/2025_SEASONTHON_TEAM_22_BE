package goormthonuniv.team_22_be.questioncard.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questioncard.domain.repository.AnswerRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static goormthonuniv.team_22_be.questioncard.domain.model.QAnswer.answer;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByMemberAndQuestionCardId(Member member, QuestionCard questionCard) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();

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
                        answer.createdAt.goe(startOfDay), // a.createdAt >= startOfDay
                        answer.createdAt.lt(endOfDay)      // a.createdAt < endOfDay
                )
                .fetchOne();

        return count != null ? count : 0;
    }
}
