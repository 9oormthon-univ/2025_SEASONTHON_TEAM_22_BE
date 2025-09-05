package goormthonuniv.team_22_be.emotion.infrastructure;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionState;
import goormthonuniv.team_22_be.emotion.domain.repository.EmotionRecordRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static goormthonuniv.team_22_be.emotion.domain.model.QEmotionRecord.emotionRecord;

@Repository
@RequiredArgsConstructor
public class EmotionRecordRepositoryImpl implements EmotionRecordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countByMemberIdAndCreatedAtBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaQueryFactory
                .select(emotionRecord.count())
                .from(emotionRecord)
                .where(
                        emotionRecord.member.id.eq(memberId),
                        emotionRecord.createdAt.between(startDate, endDate)
                )
                .groupBy(emotionRecord.emotionState)
                .fetchFirst();
    }

    @Override
    public Optional<EmotionState> findMostEmotionStateByMemberId(Long memberId) {
        Tuple result = jpaQueryFactory
                .select(emotionRecord.emotionState, emotionRecord.count())
                .from(emotionRecord)
                .where(emotionRecord.member.id.eq(memberId))
                .groupBy(emotionRecord.emotionState)
                .orderBy(emotionRecord.count().desc())
                .fetchFirst();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(result.get(emotionRecord.emotionState));
    }

    @Override
    public List<Tuple> findEmotionStatsByMonth(Long memberId, int year, int month) {
        NumberExpression<Integer> weekOfMonth = Expressions.numberTemplate(
                Integer.class,
                "FLOOR((DAYOFMONTH({0}) - 1) / 7) + 1",
                emotionRecord.createdAt
        );

        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        return jpaQueryFactory
                .select(weekOfMonth, emotionRecord.emotionState, emotionRecord.id.count())
                .from(emotionRecord)
                .where(
                        emotionRecord.member.id.eq(memberId),
                        emotionRecord.createdAt.between(start, end)
                )
                .groupBy(weekOfMonth, emotionRecord.emotionState)
                .orderBy(weekOfMonth.asc())
                .fetch();
    }
}
