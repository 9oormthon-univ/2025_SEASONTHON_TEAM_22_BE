package goormthonuniv.team_22_be.post.application.dto;

import goormthonuniv.team_22_be.post.domain.model.PostCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CreatePostRequest(
        @NotNull
        @Schema(description = "카테고리(POST/REVIEW)")
        PostCategory postCategory,

        @Size(min = 1, max = 200)
        @NotBlank
        String title,

        @NotBlank
        String content,

        @Schema(description = "리뷰일 때만 필수") Long activityId,

        @Schema(description = "리뷰일 때만 1 ~ 5")
        @Min(1) @Max(5) Integer rating

) {
}
