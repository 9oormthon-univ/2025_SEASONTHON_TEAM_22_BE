package goormthonuniv.team_22_be.post.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    
    @Test
    @DisplayName("게시글 생성 테스트")
    void createPostTest() {
        //given
        PostCategory category = PostCategory.POST;
        Long likes = 0L;
        Long rating = 0L;
        String title = "첫 번째 게시글";
        String content = "이것은 첫 번째 게시글의 내용입니다.";
        
        // when
        Post post = Post.create(null, null, category, likes, rating, title, content);

        // then
        assertNotNull(post);
        assertEquals(category, post.getCategory());
        assertEquals(likes, post.getLikes());
        assertEquals(rating, post.getRating());
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
    }
    
    @Test
    @DisplayName("게시글 업데이트 테스트")
    void updatePostTest() {
        //given
        Post post = Post.create(null, null, PostCategory.POST, 0L, 0L, "제목", "내용");
        String newTitle = "업데이트된 제목";
        String newContent = "업데이트된 내용";
        
        // when
        post.update(newTitle, newContent);
        
        // then
        assertEquals(newTitle, post.getTitle());
        assertEquals(newContent, post.getContent());
    }
    
    @Test
    @DisplayName("게시글 좋아요 테스트")
    void likePostTest() {
        //given
        Post post = Post.create(null, null, PostCategory.POST, 0L, 0L, "제목", "내용");

        // when
        post.increaseLikes();
        
        // then
        assertEquals(1L, post.getLikes());
    }

    @Test
    @DisplayName("게시글 좋아요 취소 테스트")
    void unlikePostTest() {
        //given
        Post post = Post.create(null, null, PostCategory.POST, 1L, 0L, "제목", "내용");

        // when
        post.decreaseLikes();

        // then
        assertEquals(0L, post.getLikes());
    }
}
