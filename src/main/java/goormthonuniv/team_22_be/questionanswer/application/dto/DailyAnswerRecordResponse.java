package goormthonuniv.team_22_be.questionanswer.application.dto;

import java.time.LocalDate;

public record DailyAnswerRecordResponse(

        LocalDate date,
        Long answeredCount,
        int completionRate
) {
}
