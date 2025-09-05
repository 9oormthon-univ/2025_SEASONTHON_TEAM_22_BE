package goormthonuniv.team_22_be.member.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @NotBlank
        @Size(max = 30)
        String loginId,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @Size(max = 30)
        String nickname
) {
}
