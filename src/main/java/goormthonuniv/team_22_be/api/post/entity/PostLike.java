package goormthonuniv.team_22_be.api.post.entity;

import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.post.domain.model.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_likes",
        uniqueConstraints = @UniqueConstraint (columnNames = {"member_id", "post_id"}))

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
