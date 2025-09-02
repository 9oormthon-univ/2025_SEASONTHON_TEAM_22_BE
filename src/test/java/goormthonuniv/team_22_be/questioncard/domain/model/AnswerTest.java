package goormthonuniv.team_22_be.questioncard.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    @DisplayName("질문 답변 생성 테스트")
    void createAnswerTest() {
        // given
        String content = "이것은 테스트 답변입니다.";
        
        // when
        Answer answer = Answer.create(null, null, content);
        
        // then
        assertNotNull(answer);
        assertEquals(content, answer.getContent());
    }

    @Test
    @DisplayName("질문 답변 수정 테스트")
    void updateAnswerTest() {
        // given
        String content = "이것은 테스트 답변입니다.";
        Answer answer = Answer.create(null, null, content);

        String newContent = "수정된 답변 내용입니다.";

        // when
        answer.update(newContent);

        // then
        assertEquals(newContent, answer.getContent());
    }
}