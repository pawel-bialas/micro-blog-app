package com.github.MicroBlog.service;

import com.github.MicroBlog.commons.SystemMessage;
import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.model.AccountRole;
import com.github.MicroBlog.model.AccountStatus;
import com.github.MicroBlog.model.AccountType;
import com.github.MicroBlog.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private Logger LOG = Logger.getLogger(AccountService.class.getName());

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;

    }



    public void saveUser(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setStatus(AccountStatus.ACTIVE);
        account.setType(AccountType.PUBLIC);
        if (account.getRole() == null) {
            account.setRole(AccountRole.USER);
        }

        accountRepository.save(account);
    }

    public List<Account> getUsers() {
        try {
            return accountRepository.findAll();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.userNotFoundError
            );
        }

    }


    public Account findUserById(Long id) {
        try {
            Optional<Account> byId = accountRepository.findById(id);
            if (byId.isPresent()) {
                return byId.get();
            } else throw new EntityNotFoundException(SystemMessage.userNotFoundError);
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.userNotFoundError
            );
        }

    }

    public Account findUserByLogin(String login) {
        try {
            Optional<Account> byLogin = accountRepository.findByLogin(login);
            if (byLogin.isPresent()) {
                return byLogin.get();
            } else throw new EntityNotFoundException(SystemMessage.userNotFoundError);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.userNotFoundError
            );
        }

    }

    public Account findUserByUniqueAccName(String uniqueAccName) {
        try {
            Optional<Account> byUnigueAccName = accountRepository.findByUnigueAccName(uniqueAccName);
            if (byUnigueAccName.isPresent()) {
                return byUnigueAccName.get();
            } else
                LOG.warning("Error");
                throw new EntityNotFoundException(SystemMessage.userNotFoundError);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.userNotFoundError
            );
        }

    }

    public void adminDeleteUser(Long accountId) {
        try {
            Account userById = findUserById(accountId);
            if (userById != null) {
                userById.setStatus(AccountStatus.BLOCKED);
                accountRepository.save(userById);
                LOG.info("Account login: " + userById.getLogin() + " was blocked by admin");
            } else throw new EntityNotFoundException(SystemMessage.userNotFoundError);
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.userNotFoundError
            );
        }
    }



    public void deleteAccount () {

    }

    public void makeAccountPrivate () {

    }

    public void makeAccountPublic () {

    }

    public void updateUser() {

    }

    public void changePassword(String newPassword, Principal principal) {
        try {
            Account user = findUserByLogin(principal.getName());
            if (user != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
                accountRepository.save(user);
                LOG.info("Password was changed for account: " + principal.getName());
            } else throw new EntityNotFoundException();
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    SystemMessage.userNotFoundError
            );
        }


    }
}
