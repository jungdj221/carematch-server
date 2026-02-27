package com.corp.carematch_server.global.auth;

import com.corp.carematch_server.domain.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails {

    private final User user;
    private final  String password;
    private final Map<String, Object> attributes; // OAuth2용 (선택사항)

    public PrincipalDetails(User user, String password){
        this.user = user;
        this.password = password;
        this.attributes = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        ArrayList<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(user.getUserRole()));
        return authList;
    }

    @Override
    public String getUsername(){return  user.getEmail();}


    @Override
    public String getPassword(){return  password;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
