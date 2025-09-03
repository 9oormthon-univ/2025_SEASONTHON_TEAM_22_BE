package goormthonuniv.team_22_be.emotion.application.dto;

public record CreateEmotionRecordRequest(

        String emotionState,
        String emotionText
) {
}
