package goormthonuniv.team_22_be.notification.domain.repository;
import goormthonuniv.team_22_be.notification.domain.model.PushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {
    Optional<PushSubscription> findByEndpoint(String endpoint);
    List<PushSubscription> findByMember_Id(Long memberId);
    @Query("select s from PushSubscription s where s.member.id = :memberId and s.active = true")
    List<PushSubscription> findAllActiveByMemberId(Long memberId);

}
