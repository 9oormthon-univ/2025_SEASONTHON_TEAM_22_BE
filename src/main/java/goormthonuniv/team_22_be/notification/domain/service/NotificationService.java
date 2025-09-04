package goormthonuniv.team_22_be.notification.domain.service;

import goormthonuniv.team_22_be.notification.domain.model.Notification;
import goormthonuniv.team_22_be.notification.domain.model.PushSubscription;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    /** 브라우저의 푸시 구독 정보를 저장/갱신 */
    PushSubscription upsertPushSubscription(Long memberId, String endpoint, String p256dh, String auth);

    /** 활동 찜/신청 등 이벤트 시점에 알림(하루 전/1시간 전 등) 예약 생성 */
    void scheduleActivityDeadlineReminders(Long memberId, Long activityId,
                                           LocalDateTime activityApplyEndAt);

    /** 현재 시각 기준 발송 대상(미발송 & 스케줄 도래) 조회 */
    List<Notification> findDueNotifications(LocalDateTime now);

    /** 단일 알림 발송 + 성공 시 sentAt 기록 */
    void sendAndMark(Notification notification);

    /** 배치(스케줄러/수동)로 만기 알림 전체 발송 */
    int processAndSendAllDue();
}
