package com.github.MicroBlog.controller;

import com.github.MicroBlog.model.Comment;
import com.github.MicroBlog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }


    @PostMapping(path = "/account/new-comment/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewComment(@RequestBody Comment comment, @PathVariable("id") Long postId, Principal principal) {
        commentService.saveNewComment(comment, postId, principal);

    }

    @DeleteMapping(path = "/account/delete-comment/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(Principal principal, @PathVariable("id") Long commentId) {
        commentService.deleteComment(principal,commentId);
    }

}
