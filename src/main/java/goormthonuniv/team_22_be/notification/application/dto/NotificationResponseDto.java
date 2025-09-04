package goormthonuniv.team_22_be.notification.application.dto;

import goormthonuniv.team_22_be.notification.domain.model.Notification;
import goormthonuniv.team_22_be.notification.domain.model.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponseDto(
        Long id,
        NotificationType type,
        String title,
        String body,
        LocalDateTime scheduledAt,
        LocalDateTime sentAt
) {
    public static NotificationResponseDto from(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getType(),
                notification.getTitle(),
                notification.getBody(),
                notification.getScheduledAt(),
                notification.getSentAt()
        );
    }
}
