-- liquibase formatted sql
-- changeset sanghyun:V004a-fix-activity-applications runInTransaction:false


-- 1) status 컬럼: ENUM + 기본값 보정
ALTER TABLE activity_applications
    MODIFY status ENUM('APPLIED','APPROVED','CANCELLED','REJECTED')
    NOT NULL DEFAULT 'APPLIED';

-- 2) 기존 FK 제거 (SHOW CREATE TABLE 결과의 실제 이름 사용)
--    activity_id -> FKcdp49d64ohmi5sfut7vycr0a1
--    member_id   -> FKjnypsygfcru2vdf714sr8hidk
ALTER TABLE activity_applications
DROP FOREIGN KEY FKcdp49d64ohmi5sfut7vycr0a1,
  DROP FOREIGN KEY FKjnypsygfcru2vdf714sr8hidk;

-- 3) 읽기 쉬운 이름으로 FK 재생성
ALTER TABLE activity_applications
    ADD CONSTRAINT fk_app_activity
        FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_app_member
    FOREIGN KEY (member_id)   REFERENCES members(id)   ON DELETE CASCADE;