// src/main/java/.../notification/application/NotificationServiceImpl.java
package goormthonuniv.team_22_be.notification.application;

import goormthonuniv.team_22_be.activity.domain.model.Activity;
import goormthonuniv.team_22_be.notification.domain.model.Notification;
import goormthonuniv.team_22_be.notification.domain.model.NotificationType;
import goormthonuniv.team_22_be.notification.domain.model.PushSubscription;
import goormthonuniv.team_22_be.notification.domain.service.NotificationService;
import goormthonuniv.team_22_be.notification.domain.repository.NotificationRepository;
import goormthonuniv.team_22_be.notification.domain.repository.PushSubscriptionRepository;
import goormthonuniv.team_22_be.notification.support.WebPushSender;
import goormthonuniv.team_22_be.member.domain.model.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final NotificationRepository notificationRepository;
    private final WebPushSender webPushSender;

    /**
     * 브라우저의 푸시 구독 정보를 저장/갱신(엔드포인트 기준 upsert)
     */
    @Override
    public PushSubscription upsertPushSubscription(Long memberId, String endpoint, String p256dh, String auth) {
        PushSubscription sub = pushSubscriptionRepository
                .findByEndpoint(endpoint)
                .orElseGet(() -> PushSubscription.builder()
                        .member(Member.builder().id(memberId).build())
                        .endpoint(endpoint)
                        .build());

        sub.setP256dh(p256dh);
        sub.setAuth(auth);
        sub.setActive(true);

        return pushSubscriptionRepository.save(sub);
    }

    /**
     * 활동 마감 알림 예약(예: 하루 전 & 1시간 전)
     * - 중복 예약 방지: (memberId, type, activityId, scheduledAt) 유니크 조건으로 체크
     */
    @Override
    public void scheduleActivityDeadlineReminders(Long memberId, Long activityId, LocalDateTime activityApplyEndAt) {
        if (activityApplyEndAt == null) return;

        // 하루 전
        LocalDateTime d1 = activityApplyEndAt.minusDays(1);
        if (d1.isAfter(LocalDateTime.now())) {
            upsertReminder(memberId, activityId, NotificationType.ACTIVITY_DEADLINE_D1, d1,
                    "찜한 활동 마감이 하루 남았어요",
                    "아직 고민 중이신가요? 내일이면 신청이 마감돼요!");
        }

        // 1시간 전
        LocalDateTime h1 = activityApplyEndAt.minusHours(1);
        if (h1.isAfter(LocalDateTime.now())) {
            upsertReminder(memberId, activityId, NotificationType.ACTIVITY_DEADLINE_H1, h1,
                    "찜한 활동 마감이 한 시간 남았어요",
                    "곧 신청이 마감돼요! 놓치지 마세요.");
        }
    }

    private void upsertReminder(Long memberId,
                                Long activityId,
                                NotificationType type,
                                LocalDateTime scheduledAt,
                                String title,
                                String body) {
        boolean exists = notificationRepository
                .existsByMember_IdAndTypeAndActivity_IdAndScheduledAt(memberId, type, activityId, scheduledAt);
        if (exists) return;

        Notification n = Notification.builder()
                .member(Member.builder().id(memberId).build())
                .activity(Activity.builder().id(activityId).build())          // 혹은 activity 엔티티를 직접 매핑한다면 .activity(Activity.builder().id(...).build())
                .type(type)
                .title(title)
                .body(body)
                .scheduledAt(scheduledAt)
                .sentAt(null)
                .build();

        notificationRepository.save(n);
    }

    /**
     * 현재 시각 기준 발송 대상(미발송 & 스케줄 도래)
     */
    @Override
    public List<Notification> findDueNotifications(LocalDateTime now) {
        return notificationRepository.findDue(now);
    }

    /**
     * 단일 알림 발송 + 성공 시 sentAt 기록
     * - 해당 회원의 활성 구독들로 push 전송
     * - 하나라도 성공하면 sentAt 세팅
     */
    @Override
    public void sendAndMark(Notification notification) {
        Long memberId = notification.getMember().getId();
        var subs = pushSubscriptionRepository.findAllActiveByMemberId(memberId);

        boolean anySuccess = false;
        for (PushSubscription sub : subs) {
            try {
                webPushSender.send(sub, notification.getTitle(), notification.getBody());
                anySuccess = true;
            } catch (Exception e) {
                // 실패해도 다음 구독으로 진행. (원하면 비활성화 처리/로그 기록)
                // sub.setActive(false); pushSubscriptionRepository.save(sub);
            }
        }

        if (anySuccess) {
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    /**
     * 배치(스케줄러/수동)로 만기 알림 전체 발송
     */
    @Override
    public int processAndSendAllDue() {
        List<Notification> due = findDueNotifications(LocalDateTime.now());
        int count = 0;
        for (Notification n : due) {
            sendAndMark(n);
            if (n.getSentAt() != null) count++;
        }
        return count;
    }
}