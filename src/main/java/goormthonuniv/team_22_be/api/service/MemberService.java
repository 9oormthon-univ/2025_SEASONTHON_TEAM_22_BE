package goormthonuniv.team_22_be.api.service;

import goormthonuniv.team_22_be.api.entity.Member;
import goormthonuniv.team_22_be.api.repository.MemberRepository;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
}
