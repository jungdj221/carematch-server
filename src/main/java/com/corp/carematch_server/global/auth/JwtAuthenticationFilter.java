package com.corp.carematch_server.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final PrincipalDetailsService principalDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 1. 토큰 꺼내기
        String token = parseBearerToken(request);

        // 2. 토큰이 있고, 유효?
        if(token!= null && !token.equalsIgnoreCase("null")){

            // 3. 토큰에서 이메일 꺼내기
             String email = tokenProvider.getUserEmailFromToken(token);

            // 4. DB 에서 사용자 조회
            UserDetails userDetails = principalDetailsService.loadUserByUsername(email);

            // 5. spring security 전용 인증 객체
            // 첫 번째 인자: userDetails, 두번째 : 비밀번호 인증 완료가 null 처리 가능
            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // 6. 추가정보 IP 주소, etc...
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


            // 7. security contextHolder 에 등록. 서버에서 해당 인물을 기억
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        }
        // 8. 다음 필터로 넘기기
        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
