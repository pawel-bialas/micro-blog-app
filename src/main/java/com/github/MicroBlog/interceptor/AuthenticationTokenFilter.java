package com.github.MicroBlog.interceptor;

import com.github.MicroBlog.security.JWTTokenUtil;
import com.github.MicroBlog.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JWTTokenUtil tokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authToken = request.getHeader(this.tokenHeader);

        if (authToken != null && authToken.length() > 5) {
            authToken = authToken.substring(7);
        }
        tokenUtil.getUserNameFromToken(authToken);
    }
}
