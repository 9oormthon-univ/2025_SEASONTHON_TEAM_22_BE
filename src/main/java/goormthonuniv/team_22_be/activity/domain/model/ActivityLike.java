package goormthonuniv.team_22_be.activity.domain.model;

import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "activity_likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "activity_id"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLike extends BaseTimeEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", columnDefinition = "BIGINT")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY,optional = false)
        @JoinColumn(name = "member_id", nullable = false)
        private Member member;

        @ManyToOne(fetch = FetchType.LAZY,optional = false)
        @JoinColumn(name = "activity_id", nullable = false)
        private Activity activity;
}
