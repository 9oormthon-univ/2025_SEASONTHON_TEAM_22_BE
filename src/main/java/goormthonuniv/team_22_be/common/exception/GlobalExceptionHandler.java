package goormthonuniv.team_22_be.common.exception;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(HttpServletRequest request, CustomException ex) {
        log.warn("CustomException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ex.getErrorCode());
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.fromValidation(request, ex.getBindingResult(), ErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        log.warn("ConstraintViolationException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(HttpServletRequest request, BindException ex) {
        log.warn("BindException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.fromValidation(request, ex.getBindingResult(), ErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotWritableException(HttpServletRequest request, HttpMessageNotWritableException ex) {
        log.warn("HttpMessageNotWritableException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotAcceptableException(HttpServletRequest request, HttpMediaTypeNotAcceptableException ex) {
        log.warn("HttpMediaTypeNotAcceptableException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.NOT_ACCEPTABLE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        log.warn("HttpMediaTypeNotSupportedException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.warn("HttpRequestMethodNotSupportedException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpServletRequest request, JsonParseException ex) {
        log.warn("JsonParseException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.BAD_REQUEST);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(HttpServletRequest request, DataAccessException ex) {
        log.error("DataAccessException: ", ex);
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(error.status()).body(error);
    }

    // Security 관련 예외 처리
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        log.warn("AuthenticationException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.UNAUTHORIZED);
        return ResponseEntity.status(error.status()).body(error);
    }

    // Security 관련 예외 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.warn("AccessDeniedException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.FORBIDDEN);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        log.error("Unhandled Exception: ", ex);
        ErrorResponse error = errorResponseFactory.from(request, ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(error.status()).body(error);
    }
}
