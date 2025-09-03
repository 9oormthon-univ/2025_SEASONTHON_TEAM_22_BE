package goormthonuniv.team_22_be.post.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        @Size(min=1, max=200)
        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
