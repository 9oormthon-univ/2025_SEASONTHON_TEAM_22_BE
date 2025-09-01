package goormthonuniv.team_22_be.emotion.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmotionRecordTest {

    @Test
    @DisplayName("감정 기록 생성 테스트")
    void createEmotionRecord() {
        //given
        EmotionState emotionState = EmotionState.HAPPY;
        String emotionText = "오늘은 정말 행복한 하루였어요!";
        
        // when
        EmotionRecord emotionRecord = EmotionRecord.create(null, emotionState, emotionText);
        
        // then
        assertNotNull(emotionRecord);
        assertEquals(emotionState, emotionRecord.getEmotionState());
        assertEquals(emotionText, emotionRecord.getEmotionText());
    }
}
