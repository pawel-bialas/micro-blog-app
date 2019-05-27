package com.github.MicroBlog.security.jwt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {

    private String userName;
    private Long id;
    private String token;
    private Collection <? extends GrantedAuthority> grantedAuthorities;


    public JwtUserDetails(
            String userName,
            Long id,
            String token,
            List<GrantedAuthority> grantedAuthorities) {

        this.userName = userName;
        this.id = id;
        this.token = token;
        this.grantedAuthorities = grantedAuthorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
