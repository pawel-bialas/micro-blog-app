package com.github.MicroBlog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @PostMapping(path = "/login")
    public void login () {

    }

    @PostMapping (path = "/logout")
    public void logout () {

    }

}
