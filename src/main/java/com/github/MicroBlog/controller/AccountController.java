package com.github.MicroBlog.controller;

import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
public class AccountController {

    private final AccountService accountService;


    @Autowired
    public AccountController(AccountService service) {
        this.accountService = service;
    }



    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser (@RequestBody Account account) {
        accountService.saveUser(account);
    }




    @GetMapping(path = "/users/user-id/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus (HttpStatus.OK)
    public Account findUserById (@PathVariable("id") Long id) {
        return accountService.findUserById(id);
    }

    @GetMapping(path = "/users/user-login/{login}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public Account findUserByLogin (@PathVariable ("login") String login) {
        return accountService.findUserByLogin(login);
    }

    @GetMapping(path = "/users/user-uniqe/{unique}")
    @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public Account findByUniqueName(@PathVariable ("unique") String uniqueAccName) {
        return accountService.findUserByUniqueAccName(uniqueAccName);
    }

    @PostMapping(path = "/users/change-password")
    @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public void changePassword (@RequestBody String newPassword, Principal principal) {
        accountService.changePassword(newPassword, principal);
    }

}
