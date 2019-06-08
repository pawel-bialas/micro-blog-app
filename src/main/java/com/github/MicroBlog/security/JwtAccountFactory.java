package com.github.MicroBlog.security;

import com.github.MicroBlog.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAccountFactory {
    public static JwtUser create(Account account) {
        return new JwtUser(
                account.getId(),
                account.getLogin(),
                account.getPassword(),
                mapToGrantedAuthorities(new ArrayList<String>(
                        Arrays.asList("ROLE" + account.getRole()))),
                        account.isEnabled());

    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {

        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
}
