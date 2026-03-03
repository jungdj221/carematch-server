package com.corp.carematch_server.global.auth;

import com.corp.carematch_server.domain.user.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails {

    private final Users users;
    private final  String password;
    private final Map<String, Object> attributes; // OAuth2용 (선택사항)

    public PrincipalDetails(Users users, String password){
        this.users = users;
        this.password = password;
        this.attributes = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        ArrayList<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(users.getUserRole()));
        return authList;
    }

    @Override
    public String getUsername(){return  users.getEmail();}


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
