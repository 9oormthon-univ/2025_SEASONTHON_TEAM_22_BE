package goormthonuniv.team_22_be.emotion.application.dto;

import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;

import java.time.LocalDate;

public record EmotionRecordResponse(

        Long id,
        String emotionState,
        String emotionText,
        LocalDate recordDate
) {

    public static EmotionRecordResponse from(EmotionRecord emotionRecord) {
        return new EmotionRecordResponse(
                emotionRecord.getId(),
                emotionRecord.getEmotionState().name(),
                emotionRecord.getEmotionText(),
                emotionRecord.getCreatedAt().toLocalDate()
        );
    }
}
