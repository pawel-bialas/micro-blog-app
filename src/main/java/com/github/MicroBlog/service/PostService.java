package com.github.MicroBlog.service;

import com.github.MicroBlog.commons.SystemMessage;
import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.model.ContentStatus;
import com.github.MicroBlog.model.ContentType;
import com.github.MicroBlog.model.Post;
import com.github.MicroBlog.repository.AccountRepository;
import com.github.MicroBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.message.AuthException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    private Logger LOG = Logger.getLogger(PostService.class.getName());

    @Autowired
    public PostService(PostRepository postRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    public void saveNewPost(Post post, Principal principal) {
        try {
            Long accountId = accountRepository.findByUnigueAccName(principal.getName()).get().getId();
            if (accountId != null) {
                post.setStatus(ContentStatus.NEW);
                post.setType(ContentType.BLOG_POST);
                post.setAccountId(accountId);
                postRepository.save(post);
                LOG.info(SystemMessage.newBlogPostMsg + principal.getName());
            } else throw new EntityNotFoundException(SystemMessage.userNotFoundError);
        } catch (EntityNotFoundException e) {
            LOG.info(SystemMessage.badRequestError + principal.getName());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.postNotFoundError
            );
        }
    }

    public ArrayList<Post> findAllPosts() {

        return (ArrayList<Post>) postRepository.findAll();
    }

    public void deletePost(Long postId, Principal principal) {
        try {
            Long deletingAccountId = accountRepository.findByUnigueAccName(principal.getName()).get().getId();
            Long authorAccountId = postRepository.findById(postId).get().getAccountId();
            Long currentPostId = postRepository.findById(postId).get().getId();
            if (Objects.equals(authorAccountId, deletingAccountId)) {
                if (currentPostId != null) {
                    markPostAsDeleted(postId);
                    LOG.info("post: " + postId + " was deleted by: " + principal.getName());
                } else throw new EntityNotFoundException();
            } else throw new AuthException();
        } catch (AuthException authError) {
            LOG.info(SystemMessage.unauthorizedRequestError + principal.getName());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, SystemMessage.unauthorizedRequestError);
        } catch (EntityNotFoundException notFound){
            LOG.info(SystemMessage.badRequestError + principal.getName());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.postNotFoundError
            );
        }
    }


    public void adminDeletePost(Long postId) {
        try {
            if (postRepository.findById(postId).isPresent()) {
                markPostAsDeleted(postId);
                LOG.info("post: " + postId + " was deleted by: admin");
            } else throw new EntityNotFoundException(SystemMessage.postNotFoundError);
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.postNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.postNotFoundError
            );
        }
    }

    public void adminEditPost (Long postId, String content) {
        try {
            if (postRepository.findById(postId).isPresent()) {
                editPostContent(postId,content);
                LOG.info("post: " + postId + " was edited by: admin");
            } else throw new EntityNotFoundException( SystemMessage.postNotFoundError);
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.postNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.postNotFoundError
            );
        }
    }

    public void editPost(String content, Long postId, Principal principal) {
        try {
            Long currentPost = postRepository.getOne(postId).getId();
            if (currentPost != null) {
                Long authorAccountId = postRepository.getOne(currentPost).getAccountId();
                Long editorAccountId = accountRepository.findByUnigueAccName(principal.getName()).get().getId();
                if (Objects.equals(editorAccountId, authorAccountId)) {
                    editPostContent(postId,content);
                    LOG.info("post: " + currentPost + " was edited by: " + principal.getName());
                } else throw new AuthException();
            } else throw new EntityNotFoundException();
        } catch (AuthException authError) {
            LOG.info(SystemMessage.unauthorizedRequestError + principal.getName());
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    SystemMessage.unauthorizedRequestError + principal.getName());
        } catch (EntityNotFoundException notFound) {
            LOG.info(SystemMessage.badRequestError + principal.getName());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.badRequestError + principal.getName()
            );
        }
    }

    public ArrayList<Post> findPostsByUserLogin(String login) {
        try {
            Optional<Account> byLogin = accountRepository.findByLogin(login);
            if (byLogin.isPresent()) {
                Long userId = byLogin.get().getId();
                return (ArrayList<Post>) postRepository.findByAccountId(userId);
            }
            throw new EntityNotFoundException();
        } catch (EntityNotFoundException e) {
            LOG.info(SystemMessage.postsListNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.postsListNotFoundError
            );
        }

    }

    public Post findPostById(Long postId) {
        try {
            Optional<Post> post = postRepository.findById(postId);
            if (post.isPresent()) {
                return post.get();
            } else throw new EntityNotFoundException();
        } catch (EntityNotFoundException e) {
            LOG.info(SystemMessage.postNotFoundError);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.postNotFoundError
            );
        }

    }

    private void editPostContent (Long postId, String content) {
        Post post = postRepository.findById(postId).get();
        post.setContent(content);
        post.setStatus(ContentStatus.EDITED);
        post.setEditionDate(LocalDateTime.now());
        postRepository.save(post);
    }

    private void markPostAsDeleted (Long postId){
        Post deletedPost = postRepository.getOne(postId);
        deletedPost.setEditionDate(LocalDateTime.now());
        deletedPost.setStatus(ContentStatus.DELETED);
        postRepository.save(deletedPost);
    }

}
