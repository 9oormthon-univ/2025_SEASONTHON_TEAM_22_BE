package goormthonuniv.team_22_be.emotion.application.dto;

import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;

import java.time.LocalDateTime;

public record EmotionRecordResponse(

        Long id,
        String emotionState,
        String emotionText,
        LocalDateTime recordDate
) {

    public static EmotionRecordResponse from(EmotionRecord emotionRecord) {
        return new EmotionRecordResponse(
                emotionRecord.getId(),
                emotionRecord.getEmotionState().name(),
                emotionRecord.getEmotionText(),
                emotionRecord.getCreatedAt()
        );
    }
}
