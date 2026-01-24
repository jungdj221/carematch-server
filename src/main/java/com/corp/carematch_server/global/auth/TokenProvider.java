package com.corp.carematch_server.global.auth;

import com.corp.carematch_server.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class TokenProvider {

//    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey secretKey;

    // 서버 켜질 때 고정된 키 사용
    @PostConstruct
    public void init(){
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    // accessToken
    public String createAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail()) // 사용자 식별자 (이메일)
                .claims(Map.of(
                        "userNo", user.getUserNo(), // PK
                        "role", user.getUserRole() // 권한 enum 설정시 .name까지 설정가능
                ))
                .issuedAt(new Date()) // 발급 시간
                .expiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES))) // 15분 유효
                .signWith(secretKey) // 암호화 서명
                .compact();
    }

    // refreshToken
    public String createRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail()) // 누구의 리프레시 토큰인지 식별
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS))) // 7일 유효
                .signWith(secretKey)
                .compact();
    }

    // token 에서 email 추출 - filter 에서 db 조회용
    public String getUserEmailFromToken(String token){
        return parseClaims(token).getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // 파싱 시도
            return true; // 에러 안 나면 유효함
        } catch (JwtException | IllegalArgumentException e) {
            // 서명 불일치, 만료됨, 형식 오류 등
            return false;
        }
    }


    // (내부용) Claims 파싱할때 메서드 - JJWT 0.12.x 문법 적용
    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)// 파싱
                .getPayload(); // claims 꺼내기
    }

}
