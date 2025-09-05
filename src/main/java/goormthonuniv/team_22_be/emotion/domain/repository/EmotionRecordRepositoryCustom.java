package goormthonuniv.team_22_be.emotion.domain.repository;

import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionState;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmotionRecordRepositoryCustom {

    Long countByMemberIdAndCreatedAtBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);

    public Optional<EmotionState> findMostEmotionStateByMemberId(Long memberId);
}
