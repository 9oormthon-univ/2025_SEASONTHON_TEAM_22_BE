package goormthonuniv.team_22_be.member.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String id,

        @NotBlank
        String password
) {
}
