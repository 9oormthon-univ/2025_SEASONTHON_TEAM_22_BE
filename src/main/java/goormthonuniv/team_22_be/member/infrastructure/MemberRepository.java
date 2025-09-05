package goormthonuniv.team_22_be.member.infrastructure;

import goormthonuniv.team_22_be.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndProviderUserId(String provider, String providerUserId);
}
