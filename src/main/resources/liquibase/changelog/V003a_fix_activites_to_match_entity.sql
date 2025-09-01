-- liquibase formatted sql
-- changeset sanghyun:V003a-fix-activities runInTransaction:false

-- title 50 → 200, content → TEXT, location 50 → 200, apply_* NOT NULL
ALTER TABLE activities
    MODIFY title         VARCHAR(200) NOT NULL,
    MODIFY content       TEXT         NOT NULL,
    MODIFY location      VARCHAR(200) NOT NULL,
    MODIFY apply_start_at TIMESTAMP   NOT NULL,
    MODIFY apply_end_at   TIMESTAMP   NOT NULL;

CREATE INDEX idx_activities_apply_window ON activities(apply_start_at, apply_end_at);