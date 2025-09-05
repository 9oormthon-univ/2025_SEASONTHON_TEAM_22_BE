package goormthonuniv.team_22_be.member.domain.model;

import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members",
        indexes = {
                @Index(name = "idx_members_login_id", columnList = "login_id"),
                @Index(name = "idx_members_email", columnList = "email")
        })
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 20)
    private String provider;

    @Column(name = "provider_user_id", nullable = false, length = 100)
    private String providerUserId;

    // 자체 로그인 관련 필드
    @Column(name = "login_id", length = 30)
    private String loginId;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true, length = 30)
    private String nickname;

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    public void updateNickname(String nickname) { this.nickname = nickname; }
    public void updateProfileImageUrl(String url) { this.profileImageUrl = url; }


}
