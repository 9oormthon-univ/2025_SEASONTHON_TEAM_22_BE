-- liquibase formatted sql

-- changeset Seungwon-Choi:11 runInTransaction:false
-- comment: 질문 카드에 card_type 컬럼과 CHECK 제약조건 추가

ALTER TABLE question_cards
    ADD COLUMN card_type VARCHAR(50) NOT NULL COMMENT '질문 카드 타입';

ALTER TABLE question_cards
    ADD CONSTRAINT chk_card_type
        CHECK (card_type IN ('EMOTION_UNDERSTANDING', 'SELF_UNDERSTANDING', 'RELATIONSHIP_UNDERSTANDING', 'GOAL_SETTING', 'GRATITUDE_EXPRESSION', 'FUTURE_PLANNING'));