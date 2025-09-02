package goormthonuniv.team_22_be.auth;

import goormthonuniv.team_22_be.member.domain.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer:slowmind}")
    private String issuer;

    @Value("${jwt.access-token-valid-sec:3600}")
    private long accessValidSec;

    public String createAccessToken(Member member) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessValidSec * 1000);
        var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .addClaims(Map.of(
                        "provider", member.getProvider(),
                        "pid", member.getProviderUserId()
                ))
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parse(String token) {
        var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }

}
