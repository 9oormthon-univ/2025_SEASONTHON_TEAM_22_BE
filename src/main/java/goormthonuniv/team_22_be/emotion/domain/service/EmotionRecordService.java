package goormthonuniv.team_22_be.emotion.domain.service;

import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionWeeklyStatsResponse;
import goormthonuniv.team_22_be.emotion.application.dto.MostEmotionWeekResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmotionRecordService {

    Long createEmotionRecord(Long memberId, CreateEmotionRecordRequest request);

    Page<EmotionRecordResponse> getEmotionRecords(Pageable pageable);

    MostEmotionWeekResponse getMostEmotionalThisWeek(Long memberId);

    List<EmotionWeeklyStatsResponse> getMonthlyEmotionPercentages(Long memberId, int year, int month);
}
