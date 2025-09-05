package goormthonuniv.team_22_be.member.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(description = "로그인 아이디(로컬용)", example = "mirme01")
        @NotBlank
        String id,

        @Schema(description = "비밀번호", example = "P@ssw0rd!")
        @NotBlank
        String password
) {
}
