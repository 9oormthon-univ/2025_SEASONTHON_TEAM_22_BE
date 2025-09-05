package goormthonuniv.team_22_be.emotion.application.dto;

import java.util.Map;

public record EmotionWeeklyStatsResponse(

        int week,
        Map<String, Integer> percentages
) {
}
