package com.corp.carematch_server.domain.auth.application;

import com.corp.carematch_server.domain.auth.dto.LoginRequestDTO;
import com.corp.carematch_server.domain.auth.dto.UserRequestDTO;
import com.corp.carematch_server.domain.auth.dto.UserResponseDTO;
import com.corp.carematch_server.domain.auth.entity.*;
import com.corp.carematch_server.domain.auth.repo.PasswordRepository;
import com.corp.carematch_server.domain.user.repo.UserRepository;
import com.corp.carematch_server.domain.user.entity.QUser;
import com.corp.carematch_server.domain.user.entity.User;
import com.corp.carematch_server.global.auth.TokenProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final QUser qUser = QUser.user;
    private final QCredential qCredential = QCredential.credential;

    // signup
    public UserResponseDTO signup(UserRequestDTO dto){
        // step 1; root user 정보 기입
        User user = User.builder()
                .email(dto.getEmail())
                .loginType(dto.getLoginType())
                .build();
        User newUser = userRepository.save(user);
        // step 2; password + salt
        Credential credential = Credential.builder()
                .user(User.builder()
                        .userNo(newUser.getUserNo())
                        .build())
//                .salt()
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        Credential newCredential = passwordRepository.save(credential);

        // step 3; 결과값 res
        return  UserResponseDTO.builder()
                .userNo(newUser.getUserNo())
                .email(newUser.getEmail())
                .build();

    }

    // login
    public TokenDTO login(LoginRequestDTO dto){

        Credential credentialEntity = queryFactory.selectFrom(qCredential)
                .innerJoin(qCredential.user, qUser).fetchJoin()
                .where(qUser.email.eq(dto.getEmail()))
                .fetchOne();

        if (credentialEntity == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        if(!passwordEncoder.matches(dto.getPassword(), credentialEntity.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        // 로그인 성공; 토큰발급
        User user = credentialEntity.getUser();

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
