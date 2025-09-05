package goormthonuniv.team_22_be.emotion.domain.repository;

import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmotionRecordRepositoryCustom {

    Long countByMemberIdAndCreatedAtBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    Optional<EmotionRecord> findEmotionRecordByMemberIdAndMostEmotionState(Long memberId);
}
