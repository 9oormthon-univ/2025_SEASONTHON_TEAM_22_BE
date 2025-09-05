package goormthonuniv.team_22_be.member.application.dto;

import goormthonuniv.team_22_be.member.domain.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMyInfoResponse(
        @Schema(description = "회원 ID") Long id,
        @Schema(description = "닉네임") String nickname,
        @Schema(description = "프로필 이미지 URL") String profileImageUrl
) {
    public static UpdateMyInfoResponse from(Member m) {
        return new UpdateMyInfoResponse(
                m.getId(),
                m.getNickname(),
                m.getProfileImageUrl()
        );
    }
}