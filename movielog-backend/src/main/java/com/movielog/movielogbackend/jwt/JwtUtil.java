package com.movielog.movielogbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // application.properties에 정의된 JWT 관련 설정 값들을 주입받습니다.
    @Value("${jwt.secret.key}")
    private String secretKeyString; //JWT 서명에 사용할 비밀키

    @Value("${jwt.token.expiration}")
    private long tokenExpiration; // 토큰 만료 시간(밀리초 단위)

    private SecretKey secretKey;

    // PostConstruct는 의존성 주입이 완료된 후 실행되는 메서드를 지정합니다.
    // 여기서는 주입받은 비밀키 문자열을 SecretKey 객체로 변환합니다.
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKeyString);
        secretKey = Keys.hmacShaKeyFor(bytes);
    }

    // 사용자 이메일을 받아 JWT 토큰을 생성합니다.
    public String createToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email) //토큰의 주체(사용자 이메일)
                .issuedAt(now) //토큰 발급 시간
                .expiration(new Date(now.getTime() + tokenExpiration)) // 토큰 만료 시간 설정
                .signWith(secretKey) //서명에 사용할 비밀키
                .compact(); // JWT 문자열 생성
    }

    // JWT 토큰을 검증하고, 토큰에 담긴 정보를 반환합니다.
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // 제공된 비밀키를 사용해 서명 검증
                .build()
                .parseSignedClaims(token) //토큰 파싱
                .getPayload();
    }
}
