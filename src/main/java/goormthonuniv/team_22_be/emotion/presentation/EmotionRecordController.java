package goormthonuniv.team_22_be.emotion.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.common.security.AuthMember;
import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionWeeklyStatsResponse;
import goormthonuniv.team_22_be.emotion.application.dto.MostEmotionWeekResponse;
import goormthonuniv.team_22_be.emotion.domain.service.EmotionRecordService;
import goormthonuniv.team_22_be.emotion.presentation.docs.EmotionRecordApiDocs;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emotions")
@RequiredArgsConstructor
public class EmotionRecordController implements EmotionRecordApiDocs {

    private final EmotionRecordService emotionRecordService;

    @PostMapping("/{memberId}")
    @Override
    public ResponseEntity<ApiResult<Long>> createEmotionRecord(@PathVariable Long memberId, @RequestBody CreateEmotionRecordRequest request) {
        Long id = emotionRecordService.createEmotionRecord(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(id));
    }

    @GetMapping("/{memberId}")
    @Override
    public ResponseEntity<ApiResult<PageResponse<EmotionRecordResponse>>> getEmotionRecords(@PathVariable Long memberId,
            @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<EmotionRecordResponse> page = emotionRecordService.getEmotionRecords(memberId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(PageResponse.of(page)));
    }

    @GetMapping("/{memberId}/most-week")
    @Override
    public ResponseEntity<ApiResult<MostEmotionWeekResponse>> getMostEmotionalThisWeek(@PathVariable Long memberId) {
        MostEmotionWeekResponse response = emotionRecordService.getMostEmotionalThisWeek(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(response));
    }

    @GetMapping("/{memberId}/monthly-stats")
    @Override
    public ResponseEntity<ApiResult<List<EmotionWeeklyStatsResponse>>> getMonthlyStats(
            @PathVariable Long memberId, @RequestParam(defaultValue = "2025") int year, @RequestParam int month
    ) {
        List<EmotionWeeklyStatsResponse> monthlyEmotionPercentages = emotionRecordService.getMonthlyEmotionPercentages(memberId, year, month);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(monthlyEmotionPercentages));
    }
}
