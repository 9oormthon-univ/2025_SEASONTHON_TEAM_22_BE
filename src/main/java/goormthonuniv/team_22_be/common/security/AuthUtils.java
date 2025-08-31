package goormthonuniv.team_22_be.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthUtils {
    private AuthUtils() {}

    /** JwtAuthFilter에서 principal username을 "member:{id}" 로 넣었다는 전제 */
    public static Long currentMemberIdOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }
        String name = auth.getName(); // ex) "member:123"
        if (!name.startsWith("member:")) {
            throw new IllegalStateException("알 수 없는 인증 주체 형식: " + name);
        }
        try {
            return Long.parseLong(name.substring("member:".length()));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("회원 ID 파싱 실패: " + name);
        }
    }

}
