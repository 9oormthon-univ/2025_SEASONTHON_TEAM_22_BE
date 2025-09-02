package goormthonuniv.team_22_be.api.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequestDto(
        @Size(min=1, max=200)
        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
