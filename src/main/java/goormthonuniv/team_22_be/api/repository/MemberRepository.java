package goormthonuniv.team_22_be.api.repository;

import goormthonuniv.team_22_be.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndProviderUserId(String provider, String providerUserId);
}
