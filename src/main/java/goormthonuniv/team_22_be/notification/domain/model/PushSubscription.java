package goormthonuniv.team_22_be.notification.domain.model;

import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import jakarta.persistence.*;

public class PushSubscription extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @Column(nullable = false, length=500)
    private String endpoint;

    @Column(nullable = false, length=255)
    private String p256dh;

    @Column(nullable= false, length=255)
    private String auth;
}
