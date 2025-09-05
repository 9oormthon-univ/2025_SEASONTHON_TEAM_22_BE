package goormthonuniv.team_22_be.emotion.domain.repository;

import com.querydsl.core.Tuple;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmotionRecordRepositoryCustom {

    Long countByMemberIdAndCreatedAtBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    Optional<EmotionState> findMostEmotionStateByMemberId(Long memberId);

    List<Tuple> findEmotionStatsByMonth(Long memberId, int year, int month);
}
