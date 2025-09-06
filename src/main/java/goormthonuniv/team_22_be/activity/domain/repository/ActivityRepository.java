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

}
