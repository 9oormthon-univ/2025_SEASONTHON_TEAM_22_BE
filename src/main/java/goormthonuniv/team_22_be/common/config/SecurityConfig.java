package goormthonuniv.team_22_be.common.config;

import goormthonuniv.team_22_be.common.properties.CorsProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccessHandler successHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/health", "/error").permitAll()
                        .requestMatchers(
                                "/oauth2/**",
                                "/login/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-ui.html/**",
                                "/api/v1/emotions/**", // TODO 테스트용, 추후 삭제
                                "/api/v1/question-cards/**", // TODO 테스트용, 추후 삭제
                                "/api/v1/answers/**" // TODO 테스트용, 추후 삭제
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/members/signup").permitAll()
                        .requestMatchers("/api/v1/notifications/vapid-public-key").permitAll() // TODO 테스트
                        .requestMatchers(HttpMethod.POST, "/api/v1/members/login").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 인증 실패 시 302 리다이렉트 대신 401 JSON
                .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"success\":false,\"code\":\"E401\",\"message\":\"Unauthorized\"}");
                }))

                // OAuth2 로그인 성공 시 JSON 토큰 내려주는 기존 핸들러
                .oauth2Login(o -> o
                        .loginPage("/oauth2/authorization/google")
                        .successHandler(successHandler)
                )
                ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowedOrigins(CorsProperties.ORIGINS);
        config.setAllowedMethods(CorsProperties.METHODS);
        config.setAllowedHeaders(CorsProperties.HEADERS);
        config.setExposedHeaders(CorsProperties.EXPOSEDHEADERS);
        config.setAllowCredentials(true);

        var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}