package goormthonuniv.team_22_be.emotion.application;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import goormthonuniv.team_22_be.emotion.application.dto.MostEmotionWeekResponse;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionState;
import goormthonuniv.team_22_be.emotion.domain.service.EmotionRecordService;
import goormthonuniv.team_22_be.emotion.infrastructure.EmotionRecordRepository;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class EmotionRecordServiceImpl implements EmotionRecordService {

    private final EmotionRecordRepository emotionRecordJpaRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long createEmotionRecord(Long memberId, CreateEmotionRecordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        EmotionRecord emotionRecord = EmotionRecord.create(member, EmotionState.valueOf(request.emotionState()), request.emotionText());

        return emotionRecordJpaRepository.save(emotionRecord).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EmotionRecordResponse> getEmotionRecords(Pageable pageable) {
        Page<EmotionRecord> emotionRecordPage = emotionRecordJpaRepository.findAll(pageable);
        return emotionRecordPage.map(EmotionRecordResponse::from);
    }

    @Transactional(readOnly = true)
    @Override
    public MostEmotionWeekResponse getMostEmotionalThisWeek(Long memberId) {
        LocalDate today = LocalDate.now();

        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = startOfWeek.atStartOfDay();

        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        LocalDateTime endDateTime = endOfWeek.atTime(LocalTime.MAX);

        Long count = emotionRecordJpaRepository.countByMemberIdAndCreatedAtBetween(memberId, startDateTime, endDateTime);
        EmotionState emotionState = emotionRecordJpaRepository.findMostEmotionStateByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.EMOTION_RECORD_NOT_FOUND));

        return MostEmotionWeekResponse.of(emotionState.name(), count);
    }
}
