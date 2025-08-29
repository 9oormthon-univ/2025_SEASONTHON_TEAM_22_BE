package goormthonuniv.team_22_be.common.exception;

import goormthonuniv.team_22_be.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException(사용자 정의 예외)이 발생했을 때 처리
    // → 우리가 직접 throw new CustomException(...) 한 경우 여기에 들어옴
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustom(CustomException e) {
        ErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.fail(code));
    }

    // @Valid 검증 실패 시 발생하는 예외 처리
    // (예: DTO 필드에 @NotBlank 걸어놨는데 값이 안 들어왔을 경우)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(ApiResponse.fail(ErrorCode.VALIDATION_ERROR, e.getBindingResult().toString()));
    }

    // @Validated(파라미터 유효성 검사)에서 발생하는 예외 처리
    // (예: @Min(1) int page 같은 검증 실패 시)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraint(ConstraintViolationException e) {
        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(ApiResponse.fail(ErrorCode.VALIDATION_ERROR, e.getMessage()));
    }

    // JSON 파싱 실패 같은 요청 본문을 읽을 수 없는 경우 처리
    // (예: 숫자 자리에 문자가 들어옴)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleNotReadable(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.fail(ErrorCode.BAD_REQUEST, e.getMessage()));
    }

    // 지원하지 않는 HTTP 메서드로 요청했을 때 처리
    // (예: POST만 지원하는 API를 GET으로 호출)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus())
                .body(ApiResponse.fail(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage()));
    }

    // 위에서 지정하지 않은 모든 예외를 마지막으로 처리
    // → NullPointerException 같은 예상치 못한 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleEtc(Exception e) {
        // TODO: 필요 시 로깅 추가
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(ErrorCode.INTERNAL_ERROR));
    }

}
