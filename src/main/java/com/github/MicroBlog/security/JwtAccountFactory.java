package com.github.MicroBlog.security;

import com.github.MicroBlog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAccountFactory {
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<String>(
                        Arrays.asList("ROLE" + user.getRole()))),
                        user.isEnabled());

    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {

        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
}
