package goormthonuniv.team_22_be.api.activity.dto;

import goormthonuniv.team_22_be.api.activity.entity.ActivityType;
import goormthonuniv.team_22_be.api.activity.entity.RecruitStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ActivityRequestDto(

        @NotNull(message = "활동 유형은 필수입니다.")
        ActivityType activityType,

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 최대 200자까지 입력 가능합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotBlank(message = "장소는 필수입니다.")
        @Size(max = 200, message = "장소는 최대 200자까지 입력 가능합니다.")
        String location,

        @NotNull(message = "신청 시작일은 필수입니다.")
        LocalDateTime applyStartAt,

        @NotNull(message = "신청 마감일은 필수입니다.")
        @Future(message = "마감일은 현재 시각 이후여야합니다.")
        LocalDateTime applyEndAt,

        @NotNull(message = "모집 상태는 필수입니다.")
        RecruitStatus recruitStatus
) {
}
