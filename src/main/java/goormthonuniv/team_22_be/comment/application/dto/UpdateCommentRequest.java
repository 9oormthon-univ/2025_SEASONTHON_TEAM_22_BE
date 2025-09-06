package goormthonuniv.team_22_be.comment.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(

        @Schema(description = "댓글 내용", example = "수정해요~")
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {}