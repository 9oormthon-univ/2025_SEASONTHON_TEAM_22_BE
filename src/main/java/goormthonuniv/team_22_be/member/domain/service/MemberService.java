package goormthonuniv.team_22_be.member.domain.service;

import goormthonuniv.team_22_be.member.application.dto.*;
import goormthonuniv.team_22_be.member.domain.model.Member;

public interface MemberService {
    Member upsertBySocial(String provider, String providerUserId);
    Member getByIdOrThrow(Long memberId);

    MemberResponse register(SignUpRequest request);
    MemberResponse login(LoginRequest request);

    UpdateMyInfoResponse updateProfile(Long memberId, UpdateMyInfoRequest request);
}
