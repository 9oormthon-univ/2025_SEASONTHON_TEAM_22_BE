package goormthonuniv.team_22_be.member.domain.service;

import goormthonuniv.team_22_be.member.application.dto.*;
import goormthonuniv.team_22_be.member.domain.model.Member;

public interface MemberService {
    Member upsertBySocial(String provider, String providerUserId);
    MemberResponse register(SignUpRequest request);
    MemberResponse login(LoginRequest request);
    Member getByIdOrThrow(Long memberId);
    UpdateMyInfoResponse updateProfile(Long memberId, UpdateMyInfoRequest request);

}
