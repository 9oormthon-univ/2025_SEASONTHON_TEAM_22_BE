package goormthonuniv.team_22_be.member.domain.service;

import goormthonuniv.team_22_be.member.application.dto.LoginRequest;
import goormthonuniv.team_22_be.member.application.dto.MemberResponse;
import goormthonuniv.team_22_be.member.application.dto.SignUpRequest;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoRequest;
import goormthonuniv.team_22_be.member.domain.model.Member;

public interface MemberService {
    Member upsertBySocial(String provider, String providerUserId);
    Member getByIdOrThrow(Long memberId);

    MemberResponse register(SignUpRequest request);
    MemberResponse login(LoginRequest request);
    MemberResponse updateProfile(Long memberId, UpdateMyInfoRequest request);
}
