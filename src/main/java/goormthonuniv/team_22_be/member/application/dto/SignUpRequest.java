package goormthonuniv.team_22_be.member.application.dto;

public record SignUpRequest(

        String username,
        String email,
        String password
) {
}
