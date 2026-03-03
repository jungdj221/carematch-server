package com.corp.carematch_server.domain.auth.application;

import com.corp.carematch_server.domain.auth.dto.LoginRequestDTO;
import com.corp.carematch_server.domain.auth.dto.UserRequestDTO;
import com.corp.carematch_server.domain.auth.dto.UserResponseDTO;
import com.corp.carematch_server.domain.auth.entity.*;
import com.corp.carematch_server.domain.auth.repo.PasswordDAO;
import com.corp.carematch_server.domain.auth.repo.UserDAO;
import com.corp.carematch_server.domain.user.entity.QUsers;
import com.corp.carematch_server.domain.user.entity.Users;
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
    private UserDAO userDAO;

    @Autowired
    private PasswordDAO passwordDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final QUsers qUser = QUsers.users;
    private final QCredentials qCredentials = QCredentials.credentials;

    // signup
    public UserResponseDTO signup(UserRequestDTO dto){
        // step 1; root user 정보 기입
        Users users = Users.builder()
                .email(dto.getEmail())
                .loginType(dto.getLoginType())
                .build();
        Users newUsers = userDAO.save(users);
        // step 2; password + salt
        Credentials credentials = Credentials.builder()
                .users(Users.builder()
                        .userNo(newUsers.getUserNo())
                        .build())
//                .salt()
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        Credentials newCredentials = passwordDAO.save(credentials);

        // step 3; 결과값 res
        return  UserResponseDTO.builder()
                .userNo(newUsers.getUserNo())
                .email(newUsers.getEmail())
                .build();

    }

    // login
    public TokenDTO login(LoginRequestDTO dto){

        Credentials credentialsEntity = queryFactory.selectFrom(qCredentials)
                .innerJoin(qCredentials.users, qUser).fetchJoin()
                .where(qUser.email.eq(dto.getEmail()))
                .fetchOne();

        if (credentialsEntity == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        if(!passwordEncoder.matches(dto.getPassword(), credentialsEntity.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        // 로그인 성공; 토큰발급
        Users users = credentialsEntity.getUsers();

        String accessToken = tokenProvider.createAccessToken(users);
        String refreshToken = tokenProvider.createRefreshToken(users);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .email(users.getEmail())
                .name(users.getUserName())
                .refreshToken(refreshToken)
                .build();

    }
}
