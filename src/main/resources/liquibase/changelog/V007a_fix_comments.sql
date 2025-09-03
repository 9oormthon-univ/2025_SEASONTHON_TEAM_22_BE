-- liquibase formatted sql
-- changeset sanghyun:V007a-fix-comments runInTransaction:false

ALTER TABLE comments
    DROP COLUMN account_id;

ALTER TABLE comments
    ADD COLUMN member_id BIGINT NOT NULL COMMENT '회원 FK',
    ADD CONSTRAINT fk_comments_to_members FOREIGN KEY (member_id) REFERENCES members(id);

CREATE INDEX idx_comments_member_id ON comments(member_id);