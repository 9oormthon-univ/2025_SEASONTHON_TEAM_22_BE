package goormthonuniv.team_22_be.emotion.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmotionState {

    HAPPY("행복"),
    SOSO("보통"),
    SAD("슬픔"),
    ANGER("화남"),
    WORRY("걱정");

    private final String description;
}
