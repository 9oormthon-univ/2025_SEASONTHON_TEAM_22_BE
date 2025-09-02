package goormthonuniv.team_22_be.api.post.entity;

import goormthonuniv.team_22_be.api.activity.entity.Activity;
import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.post.domain.model.PostCategory;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 (Member)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 활동 (Activity) - REVIEW일 때만 필수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    // 카테고리 (POST / REVIEW)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostCategory category;

    // 좋아요 수
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long likes = 0L;

    // 리뷰일 때 별점
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long rating;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 255)
    private String content;

    /* ==== 편의 메서드 ==== */

    public static Post create(Member member,
                              Activity activity,
                              PostCategory category,
                              String title,
                              String content,
                              Long rating) {
        return Post.builder()
                .member(member)
                .activity(activity)
                .category(category)
                .title(title)
                .content(content)
                .likes(0L)
                .rating(category == PostCategory.REVIEW ? rating : null)
                .build();
    }

    public void update(String title, String content, Long rating) {
        this.title = title;
        this.content = content;
        if (this.category == PostCategory.REVIEW && rating != null) {
            this.rating = rating;
        }
    }

    public void increaseLikes() {
        if (likes == null) likes = 0L;
        likes++;
    }

    public void decreaseLikes() {
        if (likes != null && likes > 0) likes--;
    }
}
