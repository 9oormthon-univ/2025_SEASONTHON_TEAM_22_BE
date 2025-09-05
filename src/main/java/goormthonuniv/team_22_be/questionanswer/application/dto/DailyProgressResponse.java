package goormthonuniv.team_22_be.questionanswer.application.dto;

public record DailyProgressResponse(

        Long answerQuestions,
        Long percentage
) {

    private static final int TOTAL_GOAL_COUNT = 6;

    public static DailyProgressResponse from(Long answerQuestions) {
        if (answerQuestions == null || answerQuestions <= 0) {
            return new DailyProgressResponse(0L, 0L);
        }

        double calculatedPercentage = ((double) answerQuestions / TOTAL_GOAL_COUNT) * 100;

        return new DailyProgressResponse(answerQuestions, (long) calculatedPercentage);
    }
}
