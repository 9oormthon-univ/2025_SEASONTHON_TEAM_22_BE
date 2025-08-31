package goormthonuniv.team_22_be.api.controller;

import goormthonuniv.team_22_be.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private ClientRegistrationRepository clientRegistrationRepository;

    // 프론트가 구글 로그인 URL을 받아서 redirect 할 수 있도록 제공
    @GetMapping("/login/google")
    public ApiResult<Map<String, String>> getGoogleLogiinUrl() {
        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("google");
        String url = "/oauth2/authorization/" + google.getRegistrationId();
        return ApiResult.ok(Map.of("url", url));
    }

    // 토큰 재발급 (추후 구현)
    @GetMapping("/refresh")
    public ApiResult<String> refreshToken() {
        // TODO: refresh token 로직 구현
        return ApiResult.ok("newAccessTokenHere");
    }

    // 로그아웃 (추후 구현)
    @GetMapping("/logout")
    public ApiResult<Void> logout() {
        // TODO: 서버세션이 없으니 주로 refresh token 폐기 처리
        return ApiResult.ok();
    }
}
