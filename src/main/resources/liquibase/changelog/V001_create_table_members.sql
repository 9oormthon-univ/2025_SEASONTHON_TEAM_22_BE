-- liquibase formatted sql

-- changeset Seungwon-Choi:1 runInTransaction:false

CREATE TABLE IF NOT EXISTS members (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT '사용자 PK',
    provider            VARCHAR(50)                       NOT NULL COMMENT '',
    provider_user_id    VARCHAR(255)                      NOT NULL COMMENT '',
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자'
);

CREATE UNIQUE INDEX uk_members_provider_provider_user_id ON members (provider, provider_user_id);
