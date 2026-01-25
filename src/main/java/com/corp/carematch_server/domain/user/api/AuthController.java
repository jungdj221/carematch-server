package com.corp.carematch_server.domain.user.api;

import com.corp.carematch_server.domain.user.application.UserAuthService;
import com.corp.carematch_server.domain.user.dto.LoginRequestDTO;
import com.corp.carematch_server.domain.user.dto.LoginResponseDTO;
import com.corp.carematch_server.domain.user.entity.Password;
import com.corp.carematch_server.domain.user.entity.TokenDTO;
import com.corp.carematch_server.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 인증과 관련된 모든것은 여기서 처리. 로그인
@RestController
@RequestMapping("/user-service/api/v1")
public class AuthController {

    @Autowired
    private UserAuthService userAuthService;

    // login
    @PostMapping("/public/auth/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto){

        // 1. 로그인 성공 후, access & refresh token 발급
        TokenDTO tokenDTO = userAuthService.login(dto);

        // 2. refreshToken 은 쿠키에 포장
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokenDTO.getRefreshToken())
                .httpOnly(true)
                .secure(false) // 로컬이면 false
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        // 3. accessToken, 유저정보는 따로 받음
        LoginResponseDTO response = LoginResponseDTO.builder()
                .accessToken(tokenDTO.getAccessToken())
                .email(tokenDTO.getEmail())
                .name(tokenDTO.getName())
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    // refreshToken 재발급 - test does it work? really?
}
