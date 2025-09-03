package goormthonuniv.team_22_be.emotion.domain.service;

import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmotionRecordService {

    Long createEmotionRecord(Long memberId, CreateEmotionRecordRequest request);
    Page<EmotionRecordResponse> getEmotionRecords(Pageable pageable);
}
