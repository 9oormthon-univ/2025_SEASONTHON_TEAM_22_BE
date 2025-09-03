package goormthonuniv.team_22_be.activity.domain.repository;

import goormthonuniv.team_22_be.activity.domain.model.ActivityApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    /** 특정 회원이 특정 활동에 신청했는지 여부 (중복 신청 방지용) */
    boolean existsByMember_IdAndActivity_Id(Long memberId, Long activityId);

    /** 회원-활동 기준 단건 조회 (취소/상태 변경 용도) */
    Optional<ActivityApplication> findByMember_IdAndActivity_Id(Long memberId, Long activityId);

    /** 내 신청 목록 */
    List<ActivityApplication> findAllByMember_Id(Long memberId);

}
