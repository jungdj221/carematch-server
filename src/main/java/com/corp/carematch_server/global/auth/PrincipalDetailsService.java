package com.corp.carematch_server.global.auth;

import com.corp.carematch_server.domain.auth.entity.Credentials;
import com.corp.carematch_server.domain.auth.entity.QCredentials;
import com.corp.carematch_server.domain.auth.entity.QCredentials;
import com.corp.carematch_server.domain.user.entity.QUsers;
import com.corp.carematch_server.domain.user.entity.Users;
import com.corp.carematch_server.domain.auth.repo.PasswordDAO;
import com.corp.carematch_server.domain.auth.repo.UserDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordDAO passwordDAO;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QUsers qUser = QUsers.users;
    private final QCredentials qCredentials = QCredentials.credentials;

    @Override
    @NotNull  // 무조건 return 값이 UserDetail 이라고 선언
    public UserDetails loadUserByUsername(@NotNull String email) throws UsernameNotFoundException{

//        // 1. user 조회
//        User userEntity = userDAO.findByEmail(email)
//                .orElseThrow(()-> new UsernameNotFoundException("없는 이메일 : " + email));
//
//        // 2. password 조회
//        Password passwordEntity = passwordDAO.findByUser(userEntity.getUserNo())
//                .orElseThrow(()-> new UsernameNotFoundException("비번없음"));

        Credentials credentialsEntity = queryFactory.selectFrom(qCredentials)
                .innerJoin(qCredentials.users, qUser).fetchJoin()
                .where(qUser.email.eq(email))
                .fetchOne();
        if (credentialsEntity == null) {
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다");
        }
        Users usersEntity = credentialsEntity.getUsers();
        // 3. merge
        return  new PrincipalDetails(usersEntity, credentialsEntity.getPassword());
    }

}
