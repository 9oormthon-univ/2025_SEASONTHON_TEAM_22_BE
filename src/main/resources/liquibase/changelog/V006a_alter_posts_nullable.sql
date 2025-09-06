-- liquibase formatted sql
-- changeset sanghyun:V006a-alter-posts-nullable runInTransaction:false


ALTER TABLE posts
    MODIFY COLUMN activity_id BIGINT NULL;

-- (선택) rating 컬럼을 NOT NULL + 기본값 0 으로 고정
ALTER TABLE posts
    MODIFY COLUMN rating BIGINT NOT NULL DEFAULT 0;