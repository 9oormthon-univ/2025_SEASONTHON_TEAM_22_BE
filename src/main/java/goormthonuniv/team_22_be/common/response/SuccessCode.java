package goormthonuniv.team_22_be.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    OK("S200", "요청이 성공했습니다."),
    CREATED("S201", "리소스가 생성되었습니다.");

    private final String code;
    private final String message;

}
