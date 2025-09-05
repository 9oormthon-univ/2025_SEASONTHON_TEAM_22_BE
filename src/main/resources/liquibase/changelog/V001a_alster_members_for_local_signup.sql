-- liquibase formatted sql
-- changeset sanghyun:V011-alter-members-local runInTransaction:false

ALTER TABLE members
    ADD COLUMN IF NOT EXISTS login_id           VARCHAR(30),
    ADD COLUMN IF NOT EXISTS email              VARCHAR(100),
    ADD COLUMN IF NOT EXISTS password           VARCHAR(100),
    ADD COLUMN IF NOT EXISTS nickname           VARCHAR(30)  DEFAULT '사용자',
    ADD COLUMN IF NOT EXISTS profile_image_url  VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS uk_members_login_id ON members(login_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_members_email    ON members(email);

-- 선택) 기존 레코드 중 닉네임이 NULL인 경우 기본값 채우기
UPDATE members SET nickname = '사용자' WHERE nickname IS NULL;