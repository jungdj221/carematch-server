package com.corp.carematch_server.global.auth;

import com.corp.carematch_server.domain.auth.entity.Password;
import com.corp.carematch_server.domain.auth.entity.QPassword;
import com.corp.carematch_server.domain.auth.entity.QUser;
import com.corp.carematch_server.domain.auth.entity.User;
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

    private final QUser qUser = QUser.user;
    private final QPassword qPassword = QPassword.password1;

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

        Password passwordEntity = queryFactory.selectFrom(qPassword)
                .innerJoin(qPassword.user, qUser).fetchJoin()
                .where(qUser.email.eq(email))
                .fetchOne();
        if (passwordEntity == null) {
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다");
        }
        User userEntity = passwordEntity.getUser();
        // 3. merge
        return  new PrincipalDetails(userEntity, passwordEntity.getPassword());
    }

}
