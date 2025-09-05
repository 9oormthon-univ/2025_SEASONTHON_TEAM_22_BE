package goormthonuniv.team_22_be.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PresignRequestDto(
        @Schema(example = "profile.png") @NotBlank String filename,
        @Schema(example = "image/png")   @NotBlank String contentType
) {}
