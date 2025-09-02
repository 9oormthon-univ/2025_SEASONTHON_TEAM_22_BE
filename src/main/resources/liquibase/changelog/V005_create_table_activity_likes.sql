-- liquibase formatted sql

-- changeset Seungwon-Choi:5 runInTransaction:false

CREATE TABLE IF NOT EXISTS activity_likes (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '활동 찜하기 PK',
    member_id   bigint                              NOT NULL COMMENT '회원 FK',
    activity_id bigint                              NOT NULL COMMENT '활동 FK',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_activity_likes_to_members
        FOREIGN KEY (member_id) REFERENCES members (id),

    CONSTRAINT fk_activity_likes_to_activities
        FOREIGN KEY (activity_id) REFERENCES activities (id)
);

CREATE INDEX idx_activity_likes_member_id ON activity_likes(member_id);
