-- liquibase formatted sql

-- changeset Seungwon-Choi:12

INSERT INTO question_cards(card_type, content) VALUES ('EMOTION_UNDERSTANDING', '오늘 하루 중 가장 기분 좋았던 순간이 언제인가요?');
INSERT INTO question_cards(card_type, content) VALUES ('SELF_UNDERSTANDING', '나는 언제 나답다고 느끼나요?');
INSERT INTO question_cards(card_type, content) VALUES ('RELATIONSHIP_UNDERSTANDING', '내 주변 사람들 중 가장 소중한 사람은 누구인가요? 그 이유는 무엇인가요?');
INSERT INTO question_cards(card_type, content) VALUES ('GOAL_SETTING', '내가 이루고 싶은 작은 목표가 있다면 무엇인가요?');
INSERT INTO question_cards(card_type, content) VALUES ('GRATITUDE_EXPRESSION', '오늘 감사했던 일이나 사람이 있다면 무엇인가요?');
INSERT INTO question_cards(card_type, content) VALUES ('FUTURE_PLANNING', '내일은 어떤 하루가 되었으면 좋겠나요?');

-- rollback DELETE FROM question_cards WHERE card_type IN ('EMOTION_UNDERSTANDING', 'SELF_UNDERSTANDING', 'RELATIONSHIP_UNDERSTANDING', 'GOAL_SETTING', 'GRATITUDE_EXPRESSION', 'FUTURE_PLANNING');