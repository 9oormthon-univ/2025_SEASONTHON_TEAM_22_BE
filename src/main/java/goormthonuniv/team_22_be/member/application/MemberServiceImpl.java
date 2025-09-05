package goormthonuniv.team_22_be.member.application;

import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.member.application.dto.LoginRequest;
import goormthonuniv.team_22_be.member.application.dto.MemberResponse;
import goormthonuniv.team_22_be.member.application.dto.SignUpRequest;
import goormthonuniv.team_22_be.member.application.dto.UpdateMyInfoRequest;
import goormthonuniv.team_22_be.member.domain.model.Member;
import goormthonuniv.team_22_be.member.domain.repository.MemberRepository;
import goormthonuniv.team_22_be.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member upsertBySocial(String provider, String providerUserId) {
        return memberRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .provider(provider)
                                .providerUserId(providerUserId)
                                .build()
                ));
    }

    @Transactional(readOnly = true)
    public Member getByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 일반 로그인
    @Override
    @Transactional
    public MemberResponse register(SignUpRequest request) {

        if (memberRepository.existsByProviderAndProviderUserId("LOCAL", request.loginId())) {
            throw new CustomException(ErrorCode.CONFLICT, "이미 존재하는 아이디입니다.");
        }


        // 이메일 중복 (원한다면)
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        Member m = Member.builder()
                .provider("LOCAL")
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(generateDefaultNickname())
                .build();

        return MemberResponse.from(memberRepository.save(m));
    }


    @Override
    public MemberResponse login(LoginRequest request) {

        Member member = memberRepository.findByProviderAndProviderUserId("LOCAL", request.id())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return MemberResponse.from(member);
    }

    @Override
    public MemberResponse updateProfile(Long memberId, UpdateMyInfoRequest request) {
        Member member = getByIdOrThrow(memberId);

        if (request.nickname() != null && !request.nickname().isBlank()) {
            member.setNickname(request.nickname());
        }
        if(request.profileImageUrl() != null && !request.profileImageUrl().isBlank()) {
            member.setProfileImageUrl(request.profileImageUrl());
        }
        return MemberResponse.from(member);
    }

    private String generateDefaultNickname() {
        long n = memberRepository.count() + 1;
        return "미르미" + n;
    }
}
