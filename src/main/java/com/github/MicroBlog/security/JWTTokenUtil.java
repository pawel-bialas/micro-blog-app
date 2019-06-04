package com.github.MicroBlog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = 4643030652015594187L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUserNameFromToken(String authToken) {
        String userName = null;
        try {
            final Claims claims = getClaimsFromToken(authToken);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }

        return userName;
    }

    private Claims getClaimsFromToken(String authToken) {
        Claims claims = null;
        try {
            String claimsJws;
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }
}
