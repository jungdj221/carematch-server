package com.corp.carematch_server.global.util;

import com.corp.carematch_server.domain.user.entity.User;
import com.corp.carematch_server.global.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    // instance 생성방지 ex) new 같은거
    private SecurityUtil(){ }

    // 1. 로그인 상태 확인; true:로그인 or false:비로그인
    public static boolean isLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증정보없거나 Principle = "anonymousUser"이면 비로그인
        if(auth == null || auth.getPrincipal() == null){
            return false;
        }
        // 주석설명 필요
        // 지금 들어온게 문자열이냐? yes: 비로그인 . no: 로그인
        // 실제 return 으로는 참: 로그인/ 거짓: 비로그인
        return !(auth.getPrincipal() instanceof  String);
    }

    // 2. 유저정보 추출 [일반]
    // 로그인한 유저 객체정보. 비로그인시 null 반환
    // 로그인 여부에 따라 로직이 달라지는경우
    public static User getCurrentUserOrNull(){
        if(!isLogin()){
            return null;
        }

        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

    // 2-1. 유저정보 추출 [엄격]
    // 무조건 로그인한 사람만 가능
    public static User getCurrentUser() {
        User user = getCurrentUserOrNull();

        if (user == null) {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }
        return user;
    }

    // 현재 로그인한 유저의 ID(user_no)만 빠르게 가져오기
    public static Long getCurrentUserNo(){
        return getCurrentUser().getUserNo();
    }

    // 3. 권한 체크
    // ★ Role Enum 아직 안함 ㅋㅋ
    /*
    public static boolean hasRole(Role role) {
        User user = getCurrentUserOrNull();
        if (user == null) return false;
        return user.getRole() == role;
    }
    */
}
