package goormthonuniv.team_22_be.emotion.infrastructure;

import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;
import goormthonuniv.team_22_be.emotion.domain.repository.EmotionRecordRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRecordJpaRepository extends JpaRepository<EmotionRecord, Long>, EmotionRecordRepository {
}
