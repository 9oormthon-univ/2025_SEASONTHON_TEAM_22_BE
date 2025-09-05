package goormthonuniv.team_22_be.member.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @Schema(description = "로그인 아이디 (로컬용)", example = "mirme01")
        @NotBlank
        @Size(max = 30)
        String loginId,

        @Schema(description = "이메일", example = "mirme01@example.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "비밀번호", example = "P@ssw0rd!")
        @NotBlank
        String password,

        @Size(max = 30)
        String nickname
) {
}
