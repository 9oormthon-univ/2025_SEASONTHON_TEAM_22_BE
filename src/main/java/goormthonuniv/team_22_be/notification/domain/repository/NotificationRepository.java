package goormthonuniv.team_22_be.notification.domain.repository;

import goormthonuniv.team_22_be.notification.domain.model.Notification;
import goormthonuniv.team_22_be.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("""
        select n from Notification n
        where n.sentAt is null and n.scheduledAt <= :now
        order by n.scheduledAt asc
    """)
    List<Notification> findDue(LocalDateTime now);

    boolean existsByMember_IdAndTypeAndActivity_IdAndScheduledAt(
            Long memberId,
            NotificationType type,
            Long activityId,
            LocalDateTime scheduledAt
    );
}
