package goormthonuniv.team_22_be.notification.domain.model;

import goormthonuniv.team_22_be.activity.domain.model.Activity;
import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=40)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="activity_id")
    private Activity activity;

    @Column(nullable=false, length=120)
    private String title;

    @Column(nullable=false, length=255)
    private String body;

    @Column(name="scheduled_at", nullable=false)
    private LocalDateTime scheduledAt;

    @Column(name="sent_at")
    private LocalDateTime sentAt;

    @Column(name="is_read", nullable=false)
    private boolean read = false;
}
