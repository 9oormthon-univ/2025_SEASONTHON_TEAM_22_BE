package goormthonuniv.team_22_be.member.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse(
        @Schema(description = "회원 id")
        MemberResponse member,

        @Schema(description = "JWT Access Token")
        String accessToken)
{
    public static AuthResponse of(MemberResponse member, String accessToken) {
        return new AuthResponse(member, accessToken);
    }
}
