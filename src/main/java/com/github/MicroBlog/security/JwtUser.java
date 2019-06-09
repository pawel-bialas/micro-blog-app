package com.github.MicroBlog.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.MicroBlog.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 8770962251021063338L;

    private Long id;
    private String userName;
    private String password;
    private final Collection <? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public JwtUser(Long id,
                   String userName,
                   String password,
                   Collection<? extends GrantedAuthority> authorities,
                   boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
