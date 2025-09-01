-- liquibase formatted sql

-- changeset Seungwon-Choi:4 runInTransaction:false

CREATE TABLE IF NOT EXISTS activity_applications (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '활동 신청 PK',
    member_id      bigint                              NOT NULL COMMENT '회원 FK',
    activity_id    bigint                              NOT NULL COMMENT '활동 FK',
    status         varchar(20)                         NOT NULL COMMENT '활동 신청 상태',
    applied_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_activity_applications_to_members
        FOREIGN KEY (member_id) REFERENCES members(id),

    CONSTRAINT fk_activity_applications_to_activities
        FOREIGN KEY (activity_id) REFERENCES activities(id),

    CONSTRAINT chk_status
        CHECK (status IN ('APPLIED', 'APPROVED', 'REJECTED', 'CANCELED'))
);

CREATE INDEX idx_activity_applications_member_id ON activity_applications(member_id);
