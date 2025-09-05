package goormthonuniv.team_22_be.questionanswer.domain.service;

import goormthonuniv.team_22_be.questionanswer.application.dto.AnswerResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.CreateAnswerRequest;
import goormthonuniv.team_22_be.questionanswer.application.dto.DailyAnswerRecordResponse;
import goormthonuniv.team_22_be.questionanswer.application.dto.ProgressStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AnswerService {

    Long createAnswer(Long memberId, Long questionCardId, CreateAnswerRequest request);

    Long getDailyProgress(Long memberId);

    ProgressStatusResponse getProgressStatus(Long memberId);

    Page<DailyAnswerRecordResponse> getDailyAnswerRecords(Long memberId, Pageable pageable);

    List<AnswerResponse> getAnswersByDate(Long memberId, LocalDate date);
}
