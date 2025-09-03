package goormthonuniv.team_22_be.comment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentCreateDto(
        @Schema(description = "댓글 내용", example = "좋은 글 감사합니다!")
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {}