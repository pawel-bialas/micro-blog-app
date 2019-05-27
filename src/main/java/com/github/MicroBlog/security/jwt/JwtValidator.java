package com.github.MicroBlog.security.jwt;

import com.github.MicroBlog.security.jwt.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtValidator {

    private String secret = "nameToChange";



    public JwtUser validate(String token) {
        JwtUser jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new JwtUser();
            jwtUser.setUserName(body.getSubject());
            jwtUser.setId(Long.parseLong((String)body.get("userId")));
            jwtUser.setRole((String)body.get("role"));

            return jwtUser;
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
