package goormthonuniv.team_22_be.api.activity.dto;

import goormthonuniv.team_22_be.api.activity.entity.Activity;
import goormthonuniv.team_22_be.api.activity.entity.ActivityType;
import goormthonuniv.team_22_be.api.activity.entity.RecruitStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ActivityListItemResponse(
        @Schema(description = "활동 ID")
        Long id,

        @Schema(description = "활동 유형")
        ActivityType activityType,

        @Schema(description = "제목")
        String title,

        @Schema(description = "장소")
        String location,

        @Schema(description = "찜 개수")
        Long likes,

        @Schema(description = "모집 상태")
        RecruitStatus recruitStatus,

        @Schema(description = "신청 마감일")
        LocalDateTime applyEndAt

) {
    public static ActivityListItemResponse from(Activity activity) {
        return new ActivityListItemResponse(
                activity.getId(),
                activity.getActivityType(),
                activity.getTitle(),
                activity.getLocation(),
                activity.getLikes() != null ? activity.getLikes() : 0L,
                activity.getRecruitStatus(),
                activity.getApplyEndAt()
        );
    }
}
