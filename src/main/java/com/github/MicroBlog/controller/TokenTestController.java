package com.github.MicroBlog.controller;


import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TokenTestController {


    private final AccountService accountService;

    public TokenTestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/test1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Account>getAllUsers () {
        return accountService.getUsers();
    }

    @GetMapping(value = "/test2")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Account> getUser (Principal principal) {
        Account account = accountService.findUserByLogin(principal.getName());
        return new ResponseEntity<Account>(account,HttpStatus.OK);
    }
}
