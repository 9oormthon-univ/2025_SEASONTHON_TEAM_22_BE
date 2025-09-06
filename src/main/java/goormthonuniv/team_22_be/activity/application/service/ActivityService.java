package goormthonuniv.team_22_be.activity.application.service;

import goormthonuniv.team_22_be.activity.application.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.activity.application.dto.ActivityResponseDto;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ActivityService {
    PageResponse<?> list(Pageable pageable);
    ActivityResponseDto get(Long id);
    ActivityResponseDto create(ActivityRequestDto req);
    ActivityResponseDto update(Long id, ActivityRequestDto req);
    void delete(Long id);

    void like(Long activityId);
    void unlike(Long activityId);

    void apply(Long activityId);
    void cancelApply(Long activityId);

    PageResponse<ActivityResponseDto> listMyApplied(Pageable pageable);
}