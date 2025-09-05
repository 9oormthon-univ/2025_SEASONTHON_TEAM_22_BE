package goormthonuniv.team_22_be.common.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Builder
public record ErrorResponse(

        HttpStatus status,
        String message,
        String path,
        Instant timestamp,
        List<FieldError> errors
) {

    public static ErrorResponse of(ErrorCode code, String path) {
        return ErrorResponse.builder()
                .status(code.getHttpStatus())
                .message(code.getMessage())
                .path(path)
                .timestamp(Instant.now())
                .build();
    }

    public static ErrorResponse of(ErrorCode code, String path, List<FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .status(code.getHttpStatus())
                .message(code.getMessage())
                .path(path)
                .timestamp(Instant.now())
                .errors(fieldErrors)
                .build();
    }
}
