package com.github.MicroBlog.security.login;


import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreLoginController {

    private final AccountService accountService;

    public PreLoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/login")
    public void login () {

    }

    @PostMapping (path = "/logout")
    public void logout () {

    }
}
