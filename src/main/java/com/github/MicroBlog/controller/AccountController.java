package com.github.MicroBlog.controller;

import com.github.MicroBlog.model.User;
import com.github.MicroBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
public class AccountController {

    private final UserService userService;


    @Autowired
    public AccountController(UserService service) {
        this.userService = service;
    }



    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser (@RequestBody User user) {
        userService.saveUser(user);
    }




    @GetMapping(path = "/users/user-id/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus (HttpStatus.OK)
    public User findUserById (@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping(path = "/users/user-login/{login}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public User findUserByLogin (@PathVariable ("login") String login) {
        return userService.findUserByLogin(login);
    }

    @GetMapping(path = "/users/user-uniqe/{unique}")
    @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public User findByUniqueName(@PathVariable ("unique") String uniqueAccName) {
        return userService.findUserByUniqueAccName(uniqueAccName);
    }

    @PostMapping(path = "/users/change-password")
    @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public void changePassword (@RequestBody String newPassword, Principal principal) {
        userService.changePassword(newPassword, principal);
    }

}
