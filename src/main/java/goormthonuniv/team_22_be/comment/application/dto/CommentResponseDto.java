package goormthonuniv.team_22_be.comment.application.dto;

import goormthonuniv.team_22_be.comment.domain.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentResponseDto(

        @Schema(description = "댓글 ID") Long id,
        @Schema(description = "게시글 ID") Long postId,
        @Schema(description = "작성자 회원 ID") Long memberId,
        @Schema(description = "내용") String content,
        @Schema(description = "생성일") LocalDateTime createdAt,
        @Schema(description = "수정일") LocalDateTime updatedAt
) {

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getPost().getId(),
                comment.getMember().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}