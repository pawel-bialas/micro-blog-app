package com.github.MicroBlog.controller;


import com.github.MicroBlog.model.User;
import com.github.MicroBlog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TokenTestController {


    private final UserService userService;

    public TokenTestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/test1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User>getAllUsers () {
        return userService.getUsers();
    }

    @GetMapping(value = "/test2")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<User> getUser (Principal principal) {
        User user = userService.findUserByLogin(principal.getName());
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
}
