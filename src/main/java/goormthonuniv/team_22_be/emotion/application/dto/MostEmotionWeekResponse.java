package goormthonuniv.team_22_be.emotion.application.dto;

public record MostEmotionWeekResponse(

        String emotionState,
        Long count
) {
    public static MostEmotionWeekResponse of(String name, Long count) {
        return new MostEmotionWeekResponse(name, count);
    }
}
