// src/main/java/goormthonuniv/team_22_be/member/application/MemberServiceImpl.java
package goormthonuniv.team_22_be.member.application;

import goormthonuniv.team_22_be.auth.JwtProvider;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.member.application.dto.AuthResponse;
import goormthonuniv.team_22_be.member.application.dto.LoginRequest;
import goormthonuniv.team_22_be.member.application.dto.MemberResponse;
import goormthonuniv.team_22_be.member.application.dto.MyPageResponse;
import goormthonuniv.team_22_be.member.application.dto.SignUpRequest;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoRequest;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoResponse;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.domain.repository.MemberRepository;
import goormthonuniv.team_22_be.member.domain.service.MemberService;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // ===== 소셜 로그인 지원 (OAuth2SuccessHandler 에서 사용) =====
    @Transactional
    public Member upsertBySocial(String provider, String providerUserId) {
        return memberRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .provider(provider)
                                .providerUserId(providerUserId)
                                .nickname(generateDefaultNickname())
                                .build()
                ));
    }

    @Transactional(readOnly = true)
    public Member getByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // ===== 자체 회원가입 → 즉시 JWT 발급 =====
    @Override
    public AuthResponse register(SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        String nickname = (request.nickname() == null || request.nickname().isBlank())
                ? generateDefaultNickname()
                : request.nickname();

        Member saved = memberRepository.save(
                Member.builder()
                        .provider("LOCAL")
                        .providerUserId(request.loginId()) // 자체 로그인 ID
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .nickname(nickname)
                        .build()
        );

        String token = jwtProvider.createAccessToken(saved);
        return AuthResponse.of(MemberResponse.from(saved), token);
    }

    // ===== 자체 로그인 → JWT 발급 =====
    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        // 로컬 아이디로만 로그인
        Member m = memberRepository.findByProviderAndProviderUserId("LOCAL", request.loginId())
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED, "계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.password(), m.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.createAccessToken(m);
        return AuthResponse.of(MemberResponse.from(m), token);
    }

    @Override
    @Transactional
    public UpdateMyInfoResponse updateProfile(Long memberId, UpdateMyInfoRequest request) {
        Member member = getByIdOrThrow(memberId);

        if (request.nickname() != null && !request.nickname().isBlank()) {
            member.setNickname(request.nickname());
        }
        if (request.profileImageUrl() != null && !request.profileImageUrl().isBlank()) {
            member.setProfileImageUrl(request.profileImageUrl());
        }

        // JPA 영속성 컨텍스트에 의해 dirty checking으로 업데이트 반영
        return new UpdateMyInfoResponse(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImageUrl()
        );
    }

    // ===== 기본 닉네임 생성 =====
    private String generateDefaultNickname() {
        long n = memberRepository.count() + 1;
        return "미르미" + n;
    }
}