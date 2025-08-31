package goormthonuniv.team_22_be.api.service;

import goormthonuniv.team_22_be.api.activity.service.ActivityService;

import goormthonuniv.team_22_be.api.activity.dto.ActivityListItemResponse;
import goormthonuniv.team_22_be.api.activity.dto.ActivityRequestDto;
import goormthonuniv.team_22_be.api.activity.dto.ActivityResponseDto;
import goormthonuniv.team_22_be.api.activity.entity.Activity;
import goormthonuniv.team_22_be.api.activity.entity.ActivityApplication;
import goormthonuniv.team_22_be.api.activity.entity.ActivityLike;
import goormthonuniv.team_22_be.api.activity.entity.ApplicationStatus;
import goormthonuniv.team_22_be.api.activity.repository.ActivityApplicationRepository;
import goormthonuniv.team_22_be.api.activity.repository.ActivityLikeRepository;
import goormthonuniv.team_22_be.api.activity.repository.ActivityRepository;
import goormthonuniv.team_22_be.api.entity.Member;
import goormthonuniv.team_22_be.common.exception.CustomException;
import goormthonuniv.team_22_be.common.exception.ErrorCode;
import goormthonuniv.team_22_be.common.security.AuthUtils;
import goormthonuniv.team_22_be.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityApplicationRepository applicationRepository;
    private final ActivityLikeRepository likeRepository;

    /* ========= 목록 / 상세 ========= */

    @Transactional(readOnly = true)
    @Override
    public PageResponse<?> list(Pageable pageable) {
        var page = activityRepository.findAll(pageable).map(ActivityListItemResponse::from);
        return PageResponse.of(page);
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityResponseDto get(Long id) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다. id=" + id));
        return ActivityResponseDto.from(a);
    }

    /* ========= 생성 / 수정 / 삭제 ========= */

    @Override
    public ActivityResponseDto create(ActivityRequestDto req) {
        if (req.applyEndAt().isBefore(req.applyStartAt())) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "마감일이 시작일보다 빠를 수 없습니다.");
        }

        Activity a = Activity.builder()
                .activityType(req.activityType())
                .title(req.title())
                .content(req.content())
                .location(req.location())
                .applyStartAt(req.applyStartAt())
                .applyEndAt(req.applyEndAt())
                .recruitStatus(req.recruitStatus())
                .build();

        return ActivityResponseDto.from(activityRepository.save(a));
    }

    @Override
    public ActivityResponseDto update(Long id, ActivityRequestDto req) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다. id=" + id));

        if (req.applyEndAt().isBefore(req.applyStartAt())) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "마감일이 시작일보다 빠를 수 없습니다.");
        }

        a.setActivityType(req.activityType());
        a.setTitle(req.title());
        a.setContent(req.content());
        a.setLocation(req.location());
        a.setApplyStartAt(req.applyStartAt());
        a.setApplyEndAt(req.applyEndAt());
        a.setRecruitStatus(req.recruitStatus());

        return ActivityResponseDto.from(a);
    }

    @Override
    public void delete(Long id) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다. id=" + id));
        activityRepository.delete(a); // applications/likes 는 cascade=REMOVE 로 일괄 제거
    }

    /* ========= 찜 ========= */

    @Override
    public void like(Long activityId) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다. id=" + activityId));

        if (likeRepository.existsByMember_IdAndActivity_Id(memberId, activityId)) {
            return; // 이미 찜한 상태면 멱등 처리
        }

        ActivityLike like = ActivityLike.builder()
                .member(Member.builder().id(memberId).build())
                .activity(activity)
                .build();

        try {
            likeRepository.save(like);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.CONFLICT, "이미 찜한 활동입니다.");
        }
    }

    @Override
    public void unlike(Long activityId) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        if (!likeRepository.existsByMember_IdAndActivity_Id(memberId, activityId)) {
            return; // 멱등
        }

        likeRepository.deleteByMember_IdAndActivity_Id(memberId, activityId);
    }

    /* ========= 신청 ========= */

    @Override
    public void apply(Long activityId) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "활동을 찾을 수 없습니다. id=" + activityId));

        if (!activity.isApplyOpenNow(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "신청 가능 기간이 아니거나 모집 중이 아닙니다.");
        }

        if (applicationRepository.existsByMember_IdAndActivity_Id(memberId, activityId)) {
            throw new CustomException(ErrorCode.CONFLICT, "이미 신청한 활동입니다.");
        }

        ActivityApplication app = ActivityApplication.builder()
                .member(Member.builder().id(memberId).build())
                .activity(activity)
                .status(ApplicationStatus.APPLIED)
                .build();

        try {
            applicationRepository.save(app);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.CONFLICT, "이미 신청한 활동입니다.");
        }
    }

    @Override
    public void cancelApply(Long activityId) {
        Long memberId = AuthUtils.currentMemberIdOrThrow();

        ActivityApplication app = applicationRepository.findByMember_IdAndActivity_Id(memberId, activityId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "신청 내역이 없습니다."));

        applicationRepository.delete(app); // soft-cancel 원하면 status 변경 로직으로 대체
    }
}