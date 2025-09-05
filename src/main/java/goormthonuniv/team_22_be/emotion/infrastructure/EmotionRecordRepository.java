package goormthonuniv.team_22_be.emotion.infrastructure;

import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;
import goormthonuniv.team_22_be.emotion.domain.repository.EmotionRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRecordRepository extends JpaRepository<EmotionRecord, Long>, EmotionRecordRepositoryCustom {
}
