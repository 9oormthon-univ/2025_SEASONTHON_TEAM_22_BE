package goormthonuniv.team_22_be.member.application.dto;

import jakarta.validation.constraints.Size;

public record UpdateMyInfoRequest(
        @Size(max = 30)
        String nickname,

        String profileImageUrl
) {
}
