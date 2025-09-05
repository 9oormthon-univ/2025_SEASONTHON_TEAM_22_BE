package goormthonuniv.team_22_be.notification.application;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.notification.domain.model.Notification;
import goormthonuniv.team_22_be.notification.domain.model.PushSubscription;
import goormthonuniv.team_22_be.notification.domain.repository.NotificationRepository;
import goormthonuniv.team_22_be.notification.domain.repository.PushSubscriptionRepository;
import goormthonuniv.team_22_be.notification.domain.service.NotificationService;
import goormthonuniv.team_22_be.notification.domain.model.NotificationType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final WebPushSender webPushSender; // 실제 브라우저 푸시 전송 컴포넌트(별도 @Component)

    @Override
    public PushSubscription upsertPushSubscription(Long memberId, String endpoint, String p256dh, String auth) {
        return pushSubscriptionRepository.findByMember_Id(memberId)
                .map(sub -> {
                    sub.update(endpoint, p256dh, auth);
                    return sub;
                })
                .orElseGet(() -> pushSubscriptionRepository.save(
                        PushSubscription.create(memberId, endpoint, p256dh, auth)
                ));
    }

    @Override
    public void scheduleActivityDeadlineReminders(Long memberId, Long activityId, LocalDateTime applyEndAt) {
        if (applyEndAt == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "활동 마감 시각이 없습니다.");
        }

        // 1) 하루 전
        LocalDateTime d1 = applyEndAt.minus(1, ChronoUnit.DAYS);
        scheduleOnceIfNotExists(memberId, activityId, NotificationType.ACTIVITY_DEADLINE_D1, d1,
                "찜한 활동 마감이 하루 남았어요", "아직 고민 중이신가요? 곧 신청이 마감돼요!");

        // 2) 한 시간 전
        LocalDateTime h1 = applyEndAt.minus(1, ChronoUnit.HOURS);
        scheduleOnceIfNotExists(memberId, activityId, NotificationType.ACTIVITY_DEADLINE_H1, h1,
                "마감 1시간 전", "원하신다면 지금 신청하실 수 있어요!");
    }

    private void scheduleOnceIfNotExists(Long memberId, Long activityId, NotificationType type,
                                         LocalDateTime when, String title, String body) {
        if (when.isBefore(LocalDateTime.now())) return; // 과거면 스킵
        boolean exists = notificationRepository
                .existsByMember_IdAndTypeAndActivity_IdAndScheduledAt(memberId, type, activityId, when);
        if (exists) return;

        Notification n = Notification.create(type, memberId, activityId, title, body, when);
        notificationRepository.save(n);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> findDueNotifications(LocalDateTime now) {
        return notificationRepository.findDue(now);
    }

    @Override
    public void sendAndMark(Notification n) {
        // 구독 정보
        PushSubscription sub = pushSubscriptionRepository.findByMember_Id(n.getMemberId())
                .orElse(null);
        if (sub == null) return; // 구독이 없으면 스킵(실패 카운트/로그는 필요시 추가)

        // 실제 전송
        webPushSender.send(sub, n.getTitle(), n.getBody());

        // 발송 기록
        n.markSent();
        // JPA 변경 감지로 자동 update
    }

    @Override
    public int processAndSendAllDue() {
        List<Notification> due = findDueNotifications(LocalDateTime.now());
        int sent = 0;
        for (Notification n : due) {
            try {
                sendAndMark(n);
                sent++;
            } catch (Exception e) {
                // 전송 실패 로깅/재시도 정책 등 필요 시 구현
            }
        }
        return sent;
    }
}