package goormthonuniv.team_22_be.emotion.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEmotionRecordRequest(

        @NotBlank
        String emotionState,

        @NotBlank
        String emotionText
) {
}
