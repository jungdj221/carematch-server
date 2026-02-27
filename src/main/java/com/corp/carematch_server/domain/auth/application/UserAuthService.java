package com.corp.carematch_server.domain.auth.application;

import com.corp.carematch_server.domain.auth.dto.LoginRequestDTO;
import com.corp.carematch_server.domain.auth.entity.*;
import com.corp.carematch_server.domain.auth.repo.PasswordDAO;
import com.corp.carematch_server.domain.auth.repo.UserDAO;
import com.corp.carematch_server.global.auth.TokenProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordDAO passwordDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final QUser qUser = QUser.user;
    private final QPassword qPassword = QPassword.password1;

    // 인증 login, token
    // login
    public TokenDTO login(LoginRequestDTO dto){

        Password passwordEntity = queryFactory.selectFrom(qPassword)
                .innerJoin(qPassword.user, qUser).fetchJoin()
                .where(qUser.email.eq(dto.getEmail()))
                .fetchOne();

        if (passwordEntity == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        if(!passwordEncoder.matches(dto.getPassword(), passwordEntity.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        // 로그인 성공; 토큰발급
        User user = passwordEntity.getUser();

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .email(user.getEmail())
                .name(user.getUserName())
                .refreshToken(refreshToken)
                .build();

    }
}
