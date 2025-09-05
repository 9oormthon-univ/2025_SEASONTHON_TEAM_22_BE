package goormthonuniv.team_22_be.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E500", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "E400", "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E404", "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E405", "허용되지 않은 메서드입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "E422", "검증 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E400V", "유효하지 않은 입력 값입니다."),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "E406", "허용되지 않는 요청입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "E415", "지원하지 않는 미디어 타입입니다."),

    // 인증/인가
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E403", "접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E401T", "유효하지 않거나 만료된 토큰입니다."),
    OAUTH_INVALID_CLIENT(HttpStatus.UNAUTHORIZED, "E401C", "OAuth 클라이언트가 유효하지 않습니다."),

    // 멤버/도메인
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M404", "회원 정보를 찾을 수 없습니다."),
    MEMBER_DUPLICATED(HttpStatus.CONFLICT, "M409", "이미 가입된 계정입니다."),

    QUESTION_CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Q404", "질문 카드를 찾을 수 없습니다."),
    INVALID_CARD_TYPE(HttpStatus.BAD_REQUEST, "Q400", "유효하지 않은 카드 타입입니다."),

    ANSWER_ALREADY_EXISTS(HttpStatus.CONFLICT, "A409", "이미 답변이 존재합니다."),

    // --- 추가 ---
    CONFLICT(HttpStatus.CONFLICT, "E409", "리소스 충돌이 발생했습니다."); // ex. 중복 신청, 중복 찜

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
