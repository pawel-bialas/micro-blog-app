package com.github.MicroBlog.controller;

import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.model.Post;
import com.github.MicroBlog.service.AccountService;
import com.github.MicroBlog.service.CommentService;
import com.github.MicroBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final AccountService accountService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public AdminController(
            AccountService accountService,
            PostService postService,
            CommentService commentService
    ) {
        this.accountService = accountService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @DeleteMapping(path = "/admin/delete-user/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        accountService.adminDeleteUser(id);
    }

    @GetMapping(path = "/admin/users/all")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> adminGetAllUsers() {
        return accountService.getUsers();
    }

    @PostMapping(path = "/admin/find-all-posts")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Post> adminFindAllPosts() {
        return postService.findAllPosts();
    }

    @PatchMapping (path = "/admin/edit-post/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void adminEditPost (@PathVariable ("id") Long postId, @RequestBody String content, Principal principal) {
        postService.adminEditPost(postId,content);
    }

    @DeleteMapping(path = "/admin/delete-post/{id}")
    @Secured("ROLE_ADMIN")
    public void adminDeletePost (@PathVariable ("id") Long postId, Principal principal) {
        postService.adminDeletePost(postId);
    }

    @DeleteMapping(path = "/admin/delete-comment/{id}")
    @Secured("ROLE_ADMIN")
    public void adminDeleteComment (@PathVariable ("id") Long commentId) {
        commentService.adminDeleteComment(commentId);

    }
    @PatchMapping (path = "/admin/edit-comment/{id}")
    @Secured("ROLE_ADMIN")
    public void adminEditComment (@PathVariable ("id") Long commentId, String content) {
        commentService.adminEditComment(commentId,content);
    }

}
