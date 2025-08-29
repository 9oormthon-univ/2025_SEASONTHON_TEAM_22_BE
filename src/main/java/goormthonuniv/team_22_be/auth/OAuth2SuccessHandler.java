package goormthonuniv.team_22_be.auth;

import goormthonuniv.team_22_be.api.entity.Member;
import goormthonuniv.team_22_be.api.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        try {
            var oAuth2User = (org.springframework.security.oauth2.core.user.DefaultOAuth2User) authentication.getPrincipal();
            String provider = "GOOGLE";
            String providerUserId = (String) oAuth2User.getAttribute("sub");

            // DB upsert
            var member = memberService.upsertBySocial(provider, providerUserId);

            // JWT 발급
            String accessToken = jwtProvider.createAccessToken(member);

            // JSON 응답
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
              {"success":true,"memberId":%d,"accessToken":"%s"}
              """.formatted(member.getId(), accessToken));
            response.getWriter().flush();

            // (중요) 여기서 바로 종료 → redirect 안 함
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
              {"success":false,"error":"%s"}
              """.formatted(e.getMessage()));
            response.getWriter().flush();
        }
    }
}
