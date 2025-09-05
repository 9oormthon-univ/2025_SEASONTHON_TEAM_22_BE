package goormthonuniv.team_22_be.questionanswer.domain.service;

import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyAnswerRecordResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {

    Long createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request);

    Long getDailyProgress(Long memberId);

    ProgressStatusResponse getProgressStatus(Long memberId);

    Page<DailyAnswerRecordResponse> getDailyAnswerRecords(Long memberId, Pageable pageable);
}
