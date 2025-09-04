package goormthonuniv.team_22_be.notification.application.dto;

public record PushSubscriptionRequestDto(
        String endpoint,
        String p256dh,
        String auth
) {
}
