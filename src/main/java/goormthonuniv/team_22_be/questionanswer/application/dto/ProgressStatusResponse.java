package goormthonuniv.team_22_be.questionanswer.application.dto;

public record ProgressStatusResponse(

        Long totalTrainedSessions,
        Long completedAnswers,
        Long averageCompletion
) {

    public static ProgressStatusResponse from(Long totalTrainedSessions, Long completedAnswers, Long averageCompletion) {
        return new ProgressStatusResponse(totalTrainedSessions, completedAnswers, averageCompletion);
    }
}
