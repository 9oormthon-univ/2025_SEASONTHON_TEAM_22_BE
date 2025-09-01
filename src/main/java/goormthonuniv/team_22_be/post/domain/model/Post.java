package goormthonuniv.team_22_be.post.domain.model;

import goormthonuniv.team_22_be.api.activity.entity.Activity;
import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    private Long likes;

    private Long rating;

    private String title;

    private String content;

    // 정적 팩토리 메서드
    public static Post create(Member member, Activity activity, PostCategory category, Long likes, Long rating, String title, String content) {
        return Post.builder()
                .member(member)
                .activity(activity)
                .category(category)
                .likes(likes)
                .rating(rating)
                .title(title)
                .content(content)
                .build();
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseLikes() {
        this.likes += 1;
    }

    public void decreaseLikes() {
        if (this.likes > 0) {
            this.likes -= 1;
        }
    }
}
