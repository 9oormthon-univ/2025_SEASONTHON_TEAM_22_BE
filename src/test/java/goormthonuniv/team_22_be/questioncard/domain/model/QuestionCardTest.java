package goormthonuniv.team_22_be.questioncard.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionCardTest {
    
    @Test
    @DisplayName("질문 카드 생성 테스트")
    void createQuestionCardTest() {
        // given
        String content = "이것은 테스트 질문 카드입니다.";
        
        // when
        QuestionCard questionCard = QuestionCard.create(content);
        
        // then
        assertNotNull(questionCard);
        assertEquals(content, questionCard.getContent());
    }

    @Test
    @DisplayName("질문 카드 수정 테스트")
    void updateQuestionCardTest() {
        // given
        String content = "이것은 테스트 질문 카드입니다.";
        QuestionCard questionCard = QuestionCard.create(content);

        String newContent = "수정된 질문 카드 내용입니다.";

        // when
        questionCard.update(newContent);

        // then
        assertEquals(newContent, questionCard.getContent());
    }
}
