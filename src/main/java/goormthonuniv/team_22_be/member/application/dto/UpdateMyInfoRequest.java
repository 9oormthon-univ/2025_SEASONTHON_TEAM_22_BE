package goormthonuniv.team_22_be.member.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UpdateMyInfoRequest(

        @Schema(description = "닉네임", example = "미르미7")
        @Size(max = 30)
        String nickname,

        @Schema(description = "프로필 이미지 URL")
        String profileImageUrl
) {
}
