-- liquibase formatted sql

-- changeset sanghyun:V010_create_table_post_likes runInTransaction:false

CREATE TABLE IF NOT EXISTS post_likes (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          member_id BIGINT NOT NULL,
                                          post_id BIGINT NOT NULL,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          CONSTRAINT uk_post_likes_member_post UNIQUE (member_id, post_id),
    CONSTRAINT fk_post_likes_member FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    CONSTRAINT fk_post_likes_post   FOREIGN KEY (post_id)   REFERENCES posts(id)   ON DELETE CASCADE
    );