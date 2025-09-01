-- liquibase formatted sql
-- changeset sanghyun:V007-create-activity-likes runInTransaction:false

CREATE TABLE IF NOT EXISTS activity_likes (
                                              id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '찜 PK',
                                              member_id   BIGINT NOT NULL COMMENT '회원 FK',
                                              activity_id BIGINT NOT NULL COMMENT '활동 FK',
                                              created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성일시',

                                              CONSTRAINT uk_activity_like UNIQUE (member_id, activity_id),

    CONSTRAINT fk_activity_like_member
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    CONSTRAINT fk_activity_like_activity
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
    );

CREATE INDEX idx_activity_like_member   ON activity_likes(member_id);
CREATE INDEX idx_activity_like_activity ON activity_likes(activity_id);