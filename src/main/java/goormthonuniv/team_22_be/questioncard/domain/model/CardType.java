package goormthonuniv.team_22_be.questioncard.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardType {

    EMOTION_UNDERSTANDING("감정 이해"),
    SELF_UNDERSTANDING("자기 이해"),
    RELATIONSHIP_UNDERSTANDING("관계 이해"),
    GOAL_SETTING("목표 설정"),
    GRATITUDE_EXPRESSION("감사 표현"),
    FUTURE_PLANNING("미래 계획");

    private final String description;

    // description으로 CardType 찾기, QuestionCard 등록 API에서 사용할 수 있음
//    public static CardType fromDescription(String description) {
//        return Arrays.stream(CardType.values())
//                .filter(type -> type.getDescription().equals(description))
//                .findFirst()
//                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CARD_TYPE));
//    }
}
