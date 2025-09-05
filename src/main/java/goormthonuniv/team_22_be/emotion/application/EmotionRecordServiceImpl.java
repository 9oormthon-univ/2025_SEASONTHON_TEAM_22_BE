package goormthonuniv.team_22_be.emotion.application;

import com.querydsl.core.Tuple;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionWeeklyStatsResponse;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmotionRecordServiceImpl implements EmotionRecordService {

    private final EmotionRecordRepository emotionRecordRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long createEmotionRecord(Long memberId, CreateEmotionRecordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        EmotionRecord emotionRecord = EmotionRecord.create(member, EmotionState.valueOf(request.emotionState()), request.emotionText());

        return emotionRecordRepository.save(emotionRecord).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EmotionRecordResponse> getEmotionRecords(Pageable pageable) {
        Page<EmotionRecord> emotionRecordPage = emotionRecordRepository.findAll(pageable);
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

        Long count = emotionRecordRepository.countByMemberIdAndCreatedAtBetween(memberId, startDateTime, endDateTime);
        EmotionState emotionState = emotionRecordRepository.findMostEmotionStateByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.EMOTION_RECORD_NOT_FOUND));

        return MostEmotionWeekResponse.of(emotionState.name(), count);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmotionWeeklyStatsResponse> getMonthlyEmotionPercentages(Long memberId, int year, int month) {
        List<Tuple> tuples = emotionRecordRepository.findEmotionStatsByMonth(memberId, year, month);

        Map<Integer, Map<EmotionState, Long>> countsByWeek = new HashMap<>();
        for (Tuple t : tuples) {
            Integer week = t.get(0, Integer.class);
            EmotionState state = t.get(1, EmotionState.class);
            Long cnt = t.get(2, Long.class);

            countsByWeek
                    .computeIfAbsent(week, k -> new EnumMap<>(EmotionState.class))
                    .put(state, cnt);
        }

        List<EmotionWeeklyStatsResponse> responses = new ArrayList<>();
        for (int week = 1; week <= 5; week++) {
            Map<EmotionState, Long> weekCounts = countsByWeek.getOrDefault(week, new EnumMap<>(EmotionState.class));

            for (EmotionState s : EmotionState.values()) {
                weekCounts.putIfAbsent(s, 0L);
            }

            long total = weekCounts.values().stream().mapToLong(Long::longValue).sum();
            Map<EmotionState, Integer> percentages = computeIntegerPercentages(weekCounts, total);

            Map<String, Integer> percentagesKorean = percentages.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().getDescription(),
                            Map.Entry::getValue,
                            (a,b)->b,
                            LinkedHashMap::new
                    ));

            responses.add(new EmotionWeeklyStatsResponse(week, percentagesKorean));
        }

        return responses;
    }

    private Map<EmotionState, Integer> computeIntegerPercentages(Map<EmotionState, Long> counts, long total) {
        Map<EmotionState, Integer> result = new EnumMap<>(EmotionState.class);
        if (total == 0) {
            for (EmotionState s : EmotionState.values()) result.put(s, 0);
            return result;
        }

        Map<EmotionState, Double> exact = new EnumMap<>(EmotionState.class);
        for (Map.Entry<EmotionState, Long> e : counts.entrySet()) {
            exact.put(e.getKey(), e.getValue() * 100.0 / total);
        }

        Map<EmotionState, Integer> floorMap = new EnumMap<>(EmotionState.class);
        Map<EmotionState, Double> fraction = new EnumMap<>(EmotionState.class);
        int sumFloor = 0;
        for (EmotionState s : EmotionState.values()) {
            double ex = exact.getOrDefault(s, 0.0);
            int fl = (int) Math.floor(ex);
            floorMap.put(s, fl);
            fraction.put(s, ex - fl);
            sumFloor += fl;
        }

        int remainder = 100 - sumFloor;
        List<EmotionState> sortedByFraction = fraction.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();

        for (int i = 0; i < sortedByFraction.size(); i++) {
            EmotionState s = sortedByFraction.get(i);
            int val = floorMap.get(s);
            if (i < remainder) val += 1;
            result.put(s, val);
        }
        return result;
    }
}
