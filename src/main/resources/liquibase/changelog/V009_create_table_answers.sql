-- liquibase formatted sql

-- changeset Seungwon-Choi:9 runInTransaction:false

CREATE TABLE IF NOT EXISTS answers (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL COMMENT '답변 PK',
    member_id      bigint                              NOT NULL COMMENT '회원 FK',
    question_id    bigint                              NOT NULL COMMENT '질문 카드 FK',
    content        varchar(200)                        NOT NULL COMMENT '답변 내용',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_answers_to_members
        FOREIGN KEY (member_id) REFERENCES members (id),

    CONSTRAINT fk_answers_to_question_id
        FOREIGN KEY (question_id) REFERENCES question_cards (id)
);

CREATE INDEX idx_answers_question_id ON answers(question_id);
