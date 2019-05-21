package com.github.MicroBlog.controller;

import com.github.MicroBlog.model.Post;
import com.github.MicroBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService service) {
        this.postService = service;
    }


    @PostMapping(path = "/account/new-post")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.CREATED)
    public void newPost(@RequestBody Post post,
                        Principal principal) {
        postService.saveNewPost(post, principal);
    }

    @PatchMapping(path = "/account/edit-post/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public void editPost(Principal principal,
                         @RequestBody String content,
                         @PathVariable("id") Long id) {
        postService.editPost(content, id, principal);
    }


    @DeleteMapping(path = "/account/delete-post/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(Principal principal,
                           @PathVariable("id") Long id) {
        postService.deletePost(id, principal);
    }

}
