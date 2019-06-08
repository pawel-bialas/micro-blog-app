package com.github.MicroBlog.service;

import com.github.MicroBlog.commons.SystemMessage;
import com.github.MicroBlog.model.Comment;
import com.github.MicroBlog.model.ContentStatus;
import com.github.MicroBlog.model.ContentType;
import com.github.MicroBlog.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.message.AuthException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final PostService postService;

    private Logger LOG = Logger.getLogger(CommentService.class.getName());

    public CommentService(CommentRepository commentRepository, AccountService accountService, PostService postService) {
        this.commentRepository = commentRepository;
        this.accountService = accountService;
        this.postService = postService;
    }


    public void saveNewComment(Comment comment, Long postId, Principal principal) {
        try {
            Long accountId = accountService.findUserByUniqueAccName(principal.getName()).getId();
            Long postById = postService.findPostById(postId).getId();
            if (postById != null) {
                if (accountId != null) {
                    comment.setAccountId(accountId);
                    comment.setPostId(postId);
                    comment.setType(ContentType.COMMENT);
                    comment.setStatus(ContentStatus.NEW);
                    commentRepository.save(comment);
                    LOG.info("new comment created by: " + principal.getName());
                } else {
                    LOG.info(SystemMessage.badRequestError);
                    throw new EntityNotFoundException(SystemMessage.userNotFoundError);
                }
            } else {
                LOG.info(SystemMessage.postNotFoundError);
                throw new EntityNotFoundException(SystemMessage.postNotFoundError);
            }
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteComment(Principal principal, Long commentId) {
        try {
            if (commentRepository.findById(commentId).isPresent()) {
                Comment comment = commentRepository.getOne(commentId);
                Long deletingAccountId = accountService.findUserByUniqueAccName(principal.getName()).getId();
                Long authorAccountId = comment.getAccountId();
                if (Objects.equals(deletingAccountId, authorAccountId)) {
                    comment.setStatus(ContentStatus.DELETED);
                    comment.setEditionDate(LocalDateTime.now());
                    commentRepository.save(comment);
                    LOG.info("comment id: " +commentId + " was removed by: " + principal.getName());
                } else throw new AuthException(SystemMessage.unauthorizedRequestError);
            } else throw new EntityNotFoundException(SystemMessage.commentNotFoundError);
        } catch (AuthException authError) {
            LOG.info(SystemMessage.unauthorizedRequestError);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    SystemMessage.unauthorizedRequestError
            );
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.commentNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.commentNotFoundError
            );
        }
    }

    public void editComment (Long commentId, String content, Principal principal) {
        try {
            if (commentRepository.findById(commentId).isPresent()) {
                Comment comment = commentRepository.getOne(commentId);
                Long edittingAccountId = accountService.findUserByUniqueAccName(principal.getName()).getId();
                Long authorAccountId = comment.getAccountId();
                if (Objects.equals(edittingAccountId,authorAccountId)){
                    comment.setContent(content);
                    comment.setStatus(ContentStatus.EDITED);
                    comment.setEditionDate(LocalDateTime.now());
                    commentRepository.save(comment);
                    LOG.info("comment id: " +commentId + " was edited by: " + principal.getName());
                } else throw new AuthException(SystemMessage.unauthorizedRequestError);
            } else throw new EntityNotFoundException(SystemMessage.commentNotFoundError);
        } catch (AuthException authError) {
            LOG.info(SystemMessage.unauthorizedRequestError);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    SystemMessage.unauthorizedRequestError
            );
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.commentNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.commentNotFoundError
            );
        }
    }

    public void adminDeleteComment (Long commentId) {
        try {
            if (commentRepository.findById(commentId).isPresent()) {
                Comment comment = commentRepository.getOne(commentId);
                comment.setStatus(ContentStatus.DELETED);
                comment.setEditionDate(LocalDateTime.now());
                commentRepository.save(comment);
            } else throw new EntityNotFoundException(SystemMessage.commentNotFoundError);
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.commentNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.commentNotFoundError
            );
        }
    }

    public void adminEditComment (Long commentId, String content) {
        try {
            if (commentRepository.findById(commentId).isPresent()){
                Comment comment = commentRepository.getOne(commentId);
                comment.setContent(content);
                comment.setStatus(ContentStatus.EDITED);
                comment.setEditionDate(LocalDateTime.now());
                commentRepository.save(comment);
            } else throw new EntityNotFoundException(SystemMessage.commentNotFoundError);
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.commentNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.commentNotFoundError
            );
        }
    }


    public Comment findById(Long commentId) {
        try {
            Optional<Comment> byId = commentRepository.findById(commentId);
            if (byId.isPresent()) {
                return byId.get();
            } else throw new EntityNotFoundException(SystemMessage.commentNotFoundError);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.commentNotFoundError
            );
        }
    }
}
