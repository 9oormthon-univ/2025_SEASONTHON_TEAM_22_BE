package goormthonuniv.team_22_be.common.response;

import goormthonuniv.team_22_be.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {
    private final boolean success;
    private final String code;     // "S200", "E400" 등
    private final String message;  // 사람이 읽을 메시지
    private final T data;

    // 성공
    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(true, SuccessCode.OK.getCode(), SuccessCode.OK.getMessage(), data);
    }

    public static <T> ApiResult<T> created(T data) {
        return new ApiResult<>(true, SuccessCode.CREATED.getCode(), SuccessCode.CREATED.getMessage(), data);
    }

    public static ApiResult<Void> ok() {
        return ok(null);
    }

    // 실패
    public static ApiResult<Void> fail(ErrorCode e) {
        return new ApiResult<>(false, e.getCode(), e.getMessage(), null);
    }

    public static <T> ApiResult<T> fail(ErrorCode e, T data) {
        return new ApiResult<>(false, e.getCode(), e.getMessage(), data);

    }
}