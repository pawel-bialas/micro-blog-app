package com.github.MicroBlog.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping(path = "/login")
    public void login () {

    }

    @PostMapping (path = "/logout")
    public void logout () {

    }

}
