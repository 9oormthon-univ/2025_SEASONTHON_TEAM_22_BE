-- liquibase formatted sql

-- changeset Seungwon-Choi:3 runInTransaction:false

CREATE TABLE IF NOT EXISTS activities (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '활동 PK',
    activity_type  varchar(20)                         NOT NULL COMMENT '활동 타입',
    title          varchar(50)                         NOT NULL COMMENT '활동 제목',
    content        varchar(255)                        NOT NULL COMMENT '활동 내용',
    location       varchar(50)                         NOT NULL COMMENT '활동 장소',
    likes          bigint DEFAULT 0                    NOT NULL COMMENT '찜한 유저 수',
    apply_start_at TIMESTAMP                                    COMMENT '신청 시작일자',
    apply_end_at   TIMESTAMP                                    COMMENT '신청 마감일자',
    recruit_status varchar(20)                         NOT NULL COMMENT '모집 상태',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL COMMENT '데이터 생성일자',
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT chk_activity_type
        CHECK (activity_type IN ('ALONE', 'TOGETHER')),

    CONSTRAINT chk_recruit_status
        CHECK (recruit_status IN ('OPEN', 'CLOSED', 'FINISHED'))
);

CREATE INDEX idx_activities_type_status ON activities(activity_type, recruit_status);
