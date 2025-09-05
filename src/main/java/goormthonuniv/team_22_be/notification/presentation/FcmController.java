package goormthonuniv.team_22_be.notification.presentation;

import goormthonuniv.team_22_be.common.response.ApiResult;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.notification.application.FcmService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/notifications/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;
    // 데모용 인메모리 저장소 (운영은 DB 테이블 권장: member_id, token, platform, active 등)
    private final ConcurrentHashMap<Long, String> tokenStore = new ConcurrentHashMap<>();

    // 1) 프론트가 등록토큰 보내기
    public record RegisterTokenRequest(@NotBlank String token) {}
    @PostMapping("/register-token")
    public ResponseEntity<ApiResult<Boolean>> register(@RequestBody RegisterTokenRequest req) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        tokenStore.put(memberId, req.token());
        return ResponseEntity.ok(ApiResult.ok(true));
    }

    // 2) 테스트 발송
    public record TestSendRequest(String title, String body) {}
    @PostMapping("/test")
    public ResponseEntity<ApiResult<Boolean>> test(@RequestBody TestSendRequest req) throws Exception {
        Long memberId = AuthUtils.currentMemberIdOrThrow();
        String token = tokenStore.get(memberId);
        if (token == null) return ResponseEntity.ok(ApiResult.ok(false));
        fcmService.sendToToken(token,
                req.title() == null ? "테스트 알림" : req.title(),
                req.body()  == null ? "FCM이 도착했는지 확인해주세요." : req.body(),
                null);
        return ResponseEntity.ok(ApiResult.ok(true));
    }
}