package goormthonuniv.team_22_be.notification.domain.repository;

import goormthonuniv.team_22_be.notification.domain.model.PushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {
    List<PushSubscription> findByMember_Id(Long memberId);
}
