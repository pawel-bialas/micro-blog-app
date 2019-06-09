package com.github.MicroBlog.controller;

import com.github.MicroBlog.model.User;
import com.github.MicroBlog.model.Post;
import com.github.MicroBlog.service.UserService;
import com.github.MicroBlog.service.CommentService;
import com.github.MicroBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public AdminController(
            UserService userService,
            PostService postService,
            CommentService commentService
    ) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @DeleteMapping(path = "/admin/delete-user/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.adminDeleteUser(id);
    }

    @GetMapping(path = "/admin/users/all")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public List<User> adminGetAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/admin/find-all-posts")
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
