package goormthonuniv.team_22_be.api.activity.service;

import goormthonuniv.team_22_be.api.activity.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.api.activity.dto.ActivityResponseDto;
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
}