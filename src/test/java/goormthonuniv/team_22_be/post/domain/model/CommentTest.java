package goormthonuniv.team_22_be.post.domain.model;

import goormthonuniv.team_22_be.comment.domain.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    
    @Test
    @DisplayName("댓글 생성 테스트")
    void createCommentTest() {
        // given
        String content = "테스트 댓글";

        // when
        Comment comment = Comment.create(null, null, content);

        // then
        assertNotNull(comment);
        assertEquals(content, comment.getContent());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        // given
        String accountId = "user123";
        String content = "테스트 댓글";
        Comment comment = Comment.create(null, null, content);

        String newContent = "수정 댓글";

        // when
        comment.update(newContent);

        // then
        assertEquals(newContent, comment.getContent());
    }
}
