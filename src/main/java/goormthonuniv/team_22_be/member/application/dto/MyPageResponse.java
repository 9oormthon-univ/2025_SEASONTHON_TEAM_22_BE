package goormthonuniv.team_22_be.member.application.dto;

import goormthonuniv.team_22_be.member.domain.model.Member;

public record MyPageResponse(

        String nickname,
        String email
) {
    public static MyPageResponse from(Member me) {
        return new MyPageResponse(me.getNickname(), me.getEmail());
    }
}
