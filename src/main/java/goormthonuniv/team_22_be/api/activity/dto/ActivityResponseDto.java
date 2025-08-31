package goormthonuniv.team_22_be.api.activity.dto;

import goormthonuniv.team_22_be.api.activity.entity.Activity;
import goormthonuniv.team_22_be.api.activity.entity.ActivityType;
import goormthonuniv.team_22_be.api.activity.entity.RecruitStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ActivityResponseDto(
        @Schema(description = "활동 ID")
        Long id,

        @Schema(description = "활동 유형")
        ActivityType activityType,

        @Schema(description = "제목")
        String title,

        @Schema(description = "내용")
        String content,

        @Schema(description = "장소")
        String location,

        @Schema(description = "찜 개수")
        Long likes,

        @Schema(description = "신청 시작일")
        LocalDateTime applyStartAt,

        @Schema(description = "신청 마감일")
        LocalDateTime applyEndAt,

        @Schema(description = "모집 상태")
        RecruitStatus recruitStatus,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "수정일시")
        LocalDateTime updatedAt

) {
    public static ActivityResponseDto from(Activity activity
    ) {
        return new ActivityResponseDto(
                activity.getId(),
                activity.getActivityType(),
                activity.getTitle(),
                activity.getContent(),
                activity.getLocation(),
                activity.getLikes() != null ? activity.getLikes() : 0L,
                activity.getApplyStartAt(),
                activity.getApplyEndAt(),
                activity.getRecruitStatus(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }

}
