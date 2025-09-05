package goormthonuniv.team_22_be.member.application.dto;

import goormthonuniv.team_22_be.member.domain.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;
//
public record MemberResponse(
        @Schema(description = "회원 ID") Long id,
        @Schema(description = "프로바이더(LOCAL/GOOGLE)") String provider,
        @Schema(description = "프로바이더 사용자 ID") String providerUserId,
        @Schema(description = "이메일") String email,
        @Schema(description = "닉네임") String nickname,
        @Schema(description = "프로필 이미지 URL") String profileImageUrl
) {
    public static MemberResponse from(Member m) {
        return new MemberResponse(
                m.getId(),
                m.getProvider(),
                m.getProviderUserId(),
                m.getEmail(),
                m.getNickname(),
                m.getProfileImageUrl()
        );
    }
}
