-- liquibase formatted sql
-- changeset sanghyun:V006-create-posts runInTransaction:false
CREATE TABLE IF NOT EXISTS posts (
                                     id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     member_id    BIGINT       NOT NULL,
                                     activity_id  BIGINT       NULL,
                                     category     VARCHAR(10)  NOT NULL,           -- 'POST' | 'REVIEW'
    title        VARCHAR(200) NOT NULL,
    content      TEXT         NOT NULL,
    likes        BIGINT       NOT NULL DEFAULT 0,
    rating       TINYINT      NULL,               -- 1~5, REVIEW에서만 사용
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_posts_member   FOREIGN KEY (member_id)  REFERENCES members(id)    ON DELETE CASCADE,
    CONSTRAINT fk_posts_activity FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE SET NULL,
    CONSTRAINT chk_posts_category CHECK (category IN ('POST','REVIEW'))
    );
CREATE INDEX idx_posts_category           ON posts(category);
CREATE INDEX idx_posts_member             ON posts(member_id);
CREATE INDEX idx_posts_activity           ON posts(activity_id);
