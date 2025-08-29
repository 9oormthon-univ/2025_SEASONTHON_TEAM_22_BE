package goormthonuniv.team_22_be.auth;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                Claims claims = jwtProvider.parse(token); // 유효성/서명 검증 + 클레임 추출
                String subject = claims.getSubject();     // memberId
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                var principal = new org.springframework.security.core.userdetails.User(
                        "member:" + subject, "", authorities);

                var authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 토큰 불량: 401로 응답 후 차단 (필요시 JSON 형식 통일)
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().write("{\"success\":false,\"code\":\"E401\",\"message\":\"Invalid or expired token\"}");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}