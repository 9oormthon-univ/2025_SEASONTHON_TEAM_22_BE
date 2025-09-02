package goormthonuniv.team_22_be.questioncard.domain.model;

import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import goormthonuniv.team_22_be.member.domain.model.Member;
import jakarta.persistence.Entity;
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
@Table(name = "answers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionCard questionCard;

    private String content;

    // 정적 팩토리 메서드
    public static Answer create(Member member, QuestionCard questionCard, String content) {
        return Answer.builder()
                .member(member)
                .questionCard(questionCard)
                .content(content)
                .build();
    }

    public void update(String content) {
        this.content = content;
    }
}
