package goormthonuniv.team_22_be.emotion.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.emotion.application.dto.CreateEmotionRecordRequest;
import goormthonuniv.team_22_be.emotion.application.dto.EmotionRecordResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emotions")
@RequiredArgsConstructor
public class EmotionRecordController implements EmotionRecordApiDocs {

    private final EmotionRecordService emotionRecordService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResult<Long>> createEmotionRecord(@RequestParam(name = "member-id") Long memberId, @RequestBody CreateEmotionRecordRequest request) {
        Long id = emotionRecordService.createEmotionRecord(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResult<PageResponse<EmotionRecordResponse>>> getEmotionRecords(
            @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<EmotionRecordResponse> page = emotionRecordService.getEmotionRecords(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.ok(PageResponse.of(page)));
    }
}
