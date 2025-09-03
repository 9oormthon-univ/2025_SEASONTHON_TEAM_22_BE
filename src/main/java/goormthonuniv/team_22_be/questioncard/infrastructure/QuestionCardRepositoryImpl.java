package goormthonuniv.team_22_be.questioncard.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.questioncard.domain.model.QuestionCard;
import goormthonuniv.team_22_be.questioncard.domain.repository.QuestionCardRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static goormthonuniv.team_22_be.questioncard.domain.model.QQuestionCard.questionCard;

@Repository
@RequiredArgsConstructor
public class QuestionCardRepositoryImpl implements QuestionCardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<QuestionCard> findNextCard(Long currentId) {
        QuestionCard nextCard = jpaQueryFactory
                .selectFrom(questionCard)
                .where(questionCard.id.gt(currentId))
                .orderBy(questionCard.id.asc())
                .limit(1)
                .fetchFirst();

        return Optional.ofNullable(nextCard);
    }

    @Override
    public Optional<QuestionCard> findPreviousCard(Long currentId) {
        QuestionCard previousCard = jpaQueryFactory
                .selectFrom(questionCard)
                .where(questionCard.id.lt(currentId))
                .orderBy(questionCard.id.desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(previousCard);
    }
}
