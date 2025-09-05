package goormthonuniv.team_22_be.notification.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PushSubscriptionRequestDto(
        @Schema(description = "엔드포인트 URL")
        String endpoint,
        @Schema(description = "p256dh 키")
        String p256dh,
        @Schema(description = "auth 키")
        String auth
) {
}
