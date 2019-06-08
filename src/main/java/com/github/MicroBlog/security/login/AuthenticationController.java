package com.github.MicroBlog.security.login;


import com.github.MicroBlog.commons.SystemMessage;
import com.github.MicroBlog.commons.UnauthorizedException;
import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.security.JwtTokenUtil;
import com.github.MicroBlog.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @PostMapping(value = "/login")
    public ResponseEntity<AccountDTO> login (@RequestBody Account account,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getLogin(), account.getPassword()));
            final JwtUser userDetails = (JwtUser) authenticate.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            final String token = jwtTokenUtil.generateToken(userDetails);
            response.setHeader("Token", token);
            return new ResponseEntity<AccountDTO>(new AccountDTO(userDetails.getUsername(),token), HttpStatus.OK);

        } catch (Exception e) {
         throw new UnauthorizedException(e.getMessage());
        }
    }
}
