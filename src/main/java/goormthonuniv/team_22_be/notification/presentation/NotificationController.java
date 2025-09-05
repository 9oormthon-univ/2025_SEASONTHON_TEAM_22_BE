package goormthonuniv.team_22_be.notification.presentation;

import goormthonuniv.team_22_be.common.config.VapidKeysConfig;
import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.notification.domain.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Tag(name = "Notifications", description = "웹 푸시 알림 관련 API")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final VapidKeysConfig vapidKeysConfig;

    // ─────────────────────────────────────────────────────────────────────
    // 1) 프론트가 구독 생성 시 필요한 VAPID 공개키 제공
    // ─────────────────────────────────────────────────────────────────────
    @Operation(summary = "VAPID 공개키 조회", description = "브라우저 Push 구독을 위해 Base64URL 공개키를 내려줍니다.")
    @GetMapping("/vapid-public-key")
    public ResponseEntity<ApiResult<Map<String, String>>> getPublicKey() {
        return ResponseEntity.ok(ApiResult.ok(Map.of("publicKey", vapidKeysConfig.getPublicKey())));
    }

    // ─────────────────────────────────────────────────────────────────────
    // 2) 브라우저 푸시 구독 정보 저장/갱신 (프론트에서 navigator.serviceWorker → pushManager.subscribe 한 결과 전송)
    // ─────────────────────────────────────────────────────────────────────
    @Operation(summary = "푸시 구독 저장/갱신", description = "브라우저에서 생성한 Web Push Subscription을 서버에 저장합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/subscribe")
    public ResponseEntity<ApiResult<Boolean>> subscribe(@Valid @RequestBody PushSubscriptionDto body) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        notificationService.upsertPushSubscription(
                memberId,
                body.endpoint(),
                body.keys().p256dh(),
                body.keys().auth()
        );
        return ResponseEntity.ok(ApiResult.ok(true));
    }

    // ─────────────────────────────────────────────────────────────────────
    // 3) 활동 마감 알림 예약 (하루 전/1시간 전 등은 서비스에서 계산)
    // ─────────────────────────────────────────────────────────────────────
    @Operation(summary = "활동 마감 알림 예약", description = "활동 찜/신청 시 마감 시각 기준으로 알림을 예약합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/schedule/activity-deadline")
    public ResponseEntity<ApiResult<Boolean>> scheduleActivityDeadline(@Valid @RequestBody ScheduleActivityDeadlineRequest req) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        notificationService.scheduleActivityDeadlineReminders(memberId, req.activityId(), req.applyEndAt());
        return ResponseEntity.ok(ApiResult.ok(true));
    }

    // ─────────────────────────────────────────────────────────────────────
    // 4) (개발/점검용) 내 구독으로 테스트 알림 보내기
    // ─────────────────────────────────────────────────────────────────────
    @Operation(summary = "테스트 푸시 전송", description = "내 저장된 구독들로 테스트 알림을 즉시 전송합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/test")
    public ResponseEntity<ApiResult<Integer>> sendTest() {
        // 구현 편의를 위해 서비스에 헬퍼 메서드가 없다면,
        //  - findDue가 아닌, "내 모든 active 구독에 바로 전송"하는 전용 메서드를
        //    NotificationServiceImpl에 하나 추가해서 재사용하세요.
        // 여기서는 간단히 예약/만기와 무관하게 processAndSendAllDue는 쓰지 않고,
        // 이미 구현돼 있다면 그걸 호출해도 됩니다(원하시면 바꿔도 OK).
        // 예시로는 만기 처리용 배치를 호출하지 않고, 서비스 헬퍼를 가정합니다.
        // --> 서비스에 sendTestToMember(memberId, title, body) 하나 추가 추천.

        Long memberId = AuthUtils.currentMemberIdOrThrow();
        // 가정: 서비스에 이 메서드가 있다면 사용. 없다면 만들어 두세요.
        // int sent = notificationService.sendTestToMember(memberId, "테스트 알림", "푸시가 잘 도착했는지 확인해보세요!");
        // return ResponseEntity.ok(ApiResult.ok(sent));

        // 만약 위 헬퍼가 없다면, 임시로 예약 생성 → 즉시 만기 처리 루트로도 테스트 가능:
        // 1) 지금 시각 + 1초로 마감 알림 예약
        notificationService.scheduleActivityDeadlineReminders(memberId, -1L, LocalDateTime.now().plusSeconds(1));
        // 2) 잠깐 대기 후 만기 처리 (실서버에서는 @Scheduled로 돌림)
        try { Thread.sleep(Duration.ofSeconds(2).toMillis()); } catch (InterruptedException ignored) {}
        int processed = notificationService.processAndSendAllDue();
        return ResponseEntity.ok(ApiResult.ok(processed));
    }

    // ─────────────────────────────────────────────────────────────────────
    // 5) (운영/개발 공용) 만기 알림 일괄 발송 트리거 (스케줄러 대체/보조)
    // ─────────────────────────────────────────────────────────────────────
    @Operation(summary = "만기 알림 일괄 발송", description = "현재 시각 기준 예약된(미발송) 알림을 모두 발송합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/process-due")
    public ResponseEntity<ApiResult<Integer>> processDue() {
        int sent = notificationService.processAndSendAllDue();
        return ResponseEntity.ok(ApiResult.ok(sent));
    }

    // ─────────────────────────────────────────────────────────────────────
    // Request DTOs
    // ─────────────────────────────────────────────────────────────────────
    public record PushSubscriptionDto(
            @NotBlank String endpoint,
            @NotNull Keys keys
    ) {
        public record Keys(
                @NotBlank String p256dh,
                @NotBlank String auth
        ) {}
    }

    public record ScheduleActivityDeadlineRequest(
            @NotNull Long activityId,
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applyEndAt
    ) {}
}