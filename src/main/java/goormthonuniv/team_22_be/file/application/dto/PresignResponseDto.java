package goormthonuniv.team_22_be.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record PresignResponseDto(
        @Schema(description = "S3에 PUT할 presigned URL") String url,
        @Schema(description = "업로드될 S3 key") String key,
        @Schema(description = "만료시각(UTC)") Instant expiresAt
) {
}
