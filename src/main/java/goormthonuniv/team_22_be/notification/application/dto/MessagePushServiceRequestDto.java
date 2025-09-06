package goormthonuniv.team_22_be.notification.application.dto;

import lombok.Builder;

@Builder
public record MessagePushServiceRequestDto(
        String targetToken,
        String title,
        String body
) {
    public static MessagePushServiceRequestDto of(String token, String title, String body) {
        return MessagePushServiceRequestDto.builder()
                .targetToken(token)
                .title(title)
                .body(body)
                .build();
    }
}
