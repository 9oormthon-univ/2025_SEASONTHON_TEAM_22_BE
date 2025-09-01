-- liquibase formatted sql
-- changeset sanghyun:V006-create-comments runInTransaction:false
CREATE TABLE IF NOT EXISTS comments (
                                        id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        post_id          BIGINT     NOT NULL,
                                        member_id        BIGINT     NOT NULL,
                                        comment_content  TEXT       NOT NULL,
                                        created_at       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        updated_at       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                                        CONSTRAINT fk_comments_post   FOREIGN KEY (post_id)   REFERENCES posts(id)    ON DELETE CASCADE,
    CONSTRAINT fk_comments_member FOREIGN KEY (member_id) REFERENCES members(id)  ON DELETE CASCADE
    );
CREATE INDEX idx_comments_post   ON comments(post_id);
CREATE INDEX idx_comments_member ON comments(member_id);