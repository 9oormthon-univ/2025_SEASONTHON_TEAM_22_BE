package goormthonuniv.team_22_be.common.config;

import goormthonuniv.team_22_be.auth.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http,
//                                    AuthenticationSuccessHandler successHandler,
//                                    JwtAuthFilter jwtAuthFilter) throws Exception {
//        http.csrf(csrf -> csrf.disable());
//        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/", "/health", "/error", "/oauth2/**", "/login/**").permitAll()
//                .requestMatchers("/api/secure/**").authenticated()
//                .anyRequest().permitAll()
//        );
//
//        http.oauth2Login(o -> o
//                .loginPage("/oauth2/authorization/google")
//                .successHandler(successHandler)
//        );
//
//        // UsernamePasswordAuthenticationFilter 앞에 JWT 필터 삽입
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    AuthenticationSuccessHandler successHandler) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/health", "/error", "/oauth2/**", "/login/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
        );

        http.oauth2Login(o -> o
                .loginPage("/oauth2/authorization/google")
                .successHandler(successHandler)
        );

        return http.build();
    }
}