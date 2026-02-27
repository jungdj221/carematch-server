package com.corp.carematch_server.domain.auth.application;

import com.corp.carematch_server.domain.auth.dto.UserRequestDTO;
import com.corp.carematch_server.domain.auth.dto.UserResponseDTO;
import com.corp.carematch_server.domain.auth.entity.Password;
import com.corp.carematch_server.domain.auth.entity.User;
import com.corp.carematch_server.domain.auth.repo.PasswordDAO;
import com.corp.carematch_server.domain.auth.repo.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordDAO passwordDAO;
    // 생성, 수정, 삭제. 회원가입, 유저정보 변경 로그아웃,

    private final PasswordEncoder passwordEncoder;


    public UserResponseDTO signup(UserRequestDTO dto){
        // step 1; root user 정보 기입
        User user = User.builder()
                .email(dto.getEmail())
                .loginType(dto.getLoginType())
                .build();
        User newUser = userDAO.save(user);
        // step 2; password + salt
        Password password = Password.builder()
                .user(User.builder()
                        .userNo(newUser.getUserNo())
                        .build())
//                .salt()
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        Password newPassword = passwordDAO.save(password);

        // step 3; 결과값 res
        return  UserResponseDTO.builder()
                .userNo(newUser.getUserNo())
                .email(newUser.getEmail())
                .build();

    }
}
