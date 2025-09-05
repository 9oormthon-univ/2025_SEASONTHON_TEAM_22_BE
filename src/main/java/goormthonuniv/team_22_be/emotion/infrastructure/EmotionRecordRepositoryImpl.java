package goormthonuniv.team_22_be.emotion.infrastructure;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionState;
import goormthonuniv.team_22_be.emotion.domain.repository.EmotionRecordRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
}
