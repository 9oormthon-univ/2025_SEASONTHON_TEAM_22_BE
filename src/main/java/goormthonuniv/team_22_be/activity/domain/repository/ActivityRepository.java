package goormthonuniv.team_22_be.activity.domain.repository;

import goormthonuniv.team_22_be.activity.domain.model.Activity;
import goormthonuniv.team_22_be.activity.domain.model.ActivityApplication;
import goormthonuniv.team_22_be.activity.domain.model.ActivityType;
import goormthonuniv.team_22_be.activity.domain.model.RecruitStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

    /**
     * 모집 상태(recruitStatus)별 활동 목록 조회
     * ex) 현재 모집 중(OPEN), 모집 종료(CLOSED) 등
     */
    Page<Activity> findByRecruitStatus(RecruitStatus recruitStatus, Pageable pageable);

    /**
     * 활동 유형(activityType)별 목록 조회
     * ALONE, TOGETHER 등
     */
    Page<Activity> findByActivityType(ActivityType type, Pageable pageable);
}
