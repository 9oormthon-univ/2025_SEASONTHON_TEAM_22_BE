-- liquibase formatted sql

-- changeset Seungwon-Choi:2 runInTransaction:false

CREATE TABLE IF NOT EXISTS emotion_records (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '감정 기록 PK',
    member_id     BIGINT                              NOT NULL COMMENT '회원 FK',
    emotion_state varchar(20)                         NOT NULL COMMENT '감정 기록 상태',
    emotion_text  varchar(255)                        NOT NULL COMMENT '감정 기록 텍스트',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_emotion_records_to_members
        FOREIGN KEY (member_id) REFERENCES members(id),

    CONSTRAINT chk_emotion_state
        CHECK (emotion_state IN ('HAPPY', 'SOSO', 'SAD', 'ANGER', 'WORRY'))
);

CREATE INDEX idx_emotion_records_member_id ON emotion_records(member_id);
