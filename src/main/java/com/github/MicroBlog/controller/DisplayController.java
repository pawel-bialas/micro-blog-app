package com.github.MicroBlog.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisplayController {

    @GetMapping
    @RequestMapping(path = "/welcome")
    public String welcomePageDispaly () {
        return "welcome";
    }

    @GetMapping
    @RequestMapping (path = "/register")
    public String registerFormDisplay () {
        return "register";
    }

    @GetMapping
    @RequestMapping (path = "/login")
    public String loginFormDisplay () {
        return "login";
    }

}
