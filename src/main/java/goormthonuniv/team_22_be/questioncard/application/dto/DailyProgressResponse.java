package goormthonuniv.team_22_be.questioncard.application.dto;

public record DailyProgressResponse(

        Long answerQuestions,
        Double percentage
) {
    private static final int TOTAL_GOAL_COUNT = 6;

    public static DailyProgressResponse from(Long answerQuestions) {
        if (answerQuestions == null || answerQuestions <= 0) {
            return new DailyProgressResponse(0L, 0.0);
        }

        double calculatedPercentage = ((double) answerQuestions / TOTAL_GOAL_COUNT) * 100;

        return new DailyProgressResponse(answerQuestions, calculatedPercentage);
    }
}
