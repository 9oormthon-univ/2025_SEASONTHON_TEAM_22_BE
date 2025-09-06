package goormthonuniv.team_22_be.activity.domain.repository;

import goormthonuniv.team_22_be.activity.domain.model.ActivityLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLikeRepository extends JpaRepository<ActivityLike, Long> {

    /** 특정 회원이 특정 활동을 찜했는지 (중복 찜 방지) */
    boolean existsByMember_IdAndActivity_Id(Long memberId, Long activityId);


    /** 찜 해제 */
    void deleteByMember_IdAndActivity_Id(Long memberId, Long activityId);

    Page<ActivityLike> findAllByMember_Id(Long memberId, Pageable pageable);

}
