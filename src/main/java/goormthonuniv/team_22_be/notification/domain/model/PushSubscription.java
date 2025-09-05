package goormthonuniv.team_22_be.notification.domain.model;

import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "push_subscriptions",
        indexes = {
                @Index(name = "idx_push_subscriptions_member_id", columnList = "member_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_push_subscriptions_endpoint", columnNames = "endpoint")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PushSubscription extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @Column(nullable = false, length=500)
    private String endpoint;

    @Column(nullable = false, length=255)
    private String p256dh;

    @Column(nullable= false, length=255)
    private String auth;

    @Column(nullable = false)
    private boolean active = true;

}
