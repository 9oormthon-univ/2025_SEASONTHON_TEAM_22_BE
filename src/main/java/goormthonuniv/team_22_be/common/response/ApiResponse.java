package goormthonuniv.team_22_be.common.response;

import goormthonuniv.team_22_be.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final boolean success;
    private final String code;     // "S200", "E400" 등
    private final String message;  // 사람이 읽을 메시지
    private final T data;

    // 성공
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, SuccessCode.OK.getCode(), SuccessCode.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, SuccessCode.CREATED.getCode(), SuccessCode.CREATED.getMessage(), data);
    }

    public static ApiResponse<Void> ok() {
        return ok(null);
    }

    // 실패
    public static ApiResponse<Void> fail(ErrorCode e) {
        return new ApiResponse<>(false, e.getCode(), e.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode e, T data) {
        return new ApiResponse<>(false, e.getCode(), e.getMessage(), data);

    }
}