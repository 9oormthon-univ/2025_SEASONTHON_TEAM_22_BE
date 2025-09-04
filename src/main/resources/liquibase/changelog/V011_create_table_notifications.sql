-- liquibase formatted sql

-- changeset sanghyun:V011 runInTransaction:false


CREATE TABLE IF NOT EXISTS notifications (
                                             id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             member_id    BIGINT       NOT NULL,
                                             type         VARCHAR(40)  NOT NULL,       -- 'LIKE_DEADLINE'
    activity_id  BIGINT       NULL,
    title        VARCHAR(120) NOT NULL,
    body         VARCHAR(255) NOT NULL,
    scheduled_at DATETIME     NOT NULL,
    sent_at      DATETIME     NULL,
    is_read      TINYINT(1)   NOT NULL DEFAULT 0,
    created_at   DATETIME     NOT NULL,
    updated_at   DATETIME     NOT NULL,
    CONSTRAINT fk_notifications_member FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT fk_notifications_activity FOREIGN KEY (activity_id) REFERENCES activities(id),
    CONSTRAINT uk_member_type_activity_scheduled UNIQUE (member_id, type, activity_id, scheduled_at)
    );

CREATE INDEX idx_notifs_due ON notifications(scheduled_at, sent_at);
