package goormthonuniv.team_22_be.emotion.application;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionRecord;
import goormthonuniv.team_22_be.emotion.domain.model.EmotionState;
import goormthonuniv.team_22_be.emotion.domain.service.EmotionRecordService;
import goormthonuniv.team_22_be.emotion.infrastructure.EmotionRecordJpaRepository;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.infrastructure.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmotionRecordServiceImpl implements EmotionRecordService {

    private final EmotionRecordJpaRepository emotionRecordJpaRepository;
    private final MemberJpaRepository memberRepository;

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
}
