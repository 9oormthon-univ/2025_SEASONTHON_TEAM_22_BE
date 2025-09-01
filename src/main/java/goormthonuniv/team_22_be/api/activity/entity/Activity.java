package goormthonuniv.team_22_be.api.activity.entity;

import goormthonuniv.team_22_be.common.utils.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, length = 20)
    private ActivityType activityType;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Builder.Default
    @Column(name = "likes", nullable = false)
    private Long likes = 0L;

    @Column(name = "apply_start_at", nullable = false)
    private LocalDateTime applyStartAt;

    @Column(name = "apply_end_at", nullable = false)
    private LocalDateTime applyEndAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_status", nullable = false, length = 20)
    private RecruitStatus recruitStatus;

    @OneToMany(
            mappedBy = "activity",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<ActivityApplication> applicationList = new ArrayList<>();

    @OneToMany(
            mappedBy = "activity",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<ActivityLike> likeList = new ArrayList<>();


    /*
    편의 메서드
     */
    public void increaseLikes() {
        if(likes == null) likes = 0L;
        likes++;
    }

    public void decreaseLikes() {
        if(likes == null || likes == 0) return;
        likes--;
    }

    /**
     * 지금 시각 기준으로 신청 가능한지 여부를 반환
     * - 모집 상태가 OPEN 이어야 함
     * - 시작일이 있으면 now >= start
     * - 마감일이 있으면 now <= end
     */
    public boolean isApplyOpenNow(LocalDateTime now) {
        if (this.recruitStatus != RecruitStatus.OPEN) return false;

        boolean afterStart = (applyStartAt == null) || !now.isBefore(applyStartAt);
        boolean beforeEnd  = (applyEndAt   == null) || !now.isAfter(applyEndAt);

        return afterStart && beforeEnd;
    }

}
