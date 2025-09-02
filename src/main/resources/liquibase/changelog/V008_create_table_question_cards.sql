-- liquibase formatted sql

-- changeset Seungwon-Choi:8 runInTransaction:false

CREATE TABLE IF NOT EXISTS question_cards (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '질문 카드 PK',
    content       varchar(200)                        NOT NULL COMMENT '질문 문구',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자'
);

CREATE INDEX idx_question_cards_id ON question_cards(id);
