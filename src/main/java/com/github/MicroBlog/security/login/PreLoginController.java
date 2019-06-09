package com.github.MicroBlog.security.login;


import com.github.MicroBlog.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreLoginController {

    private final UserService userService;

    public PreLoginController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping (path = "/logout")
    public void logout () {

    }
}
