package com.github.MicroBlog.security;

import com.github.MicroBlog.model.Account;
import com.github.MicroBlog.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByLogin(login);
        if (!account.isPresent()) {
            throw new UsernameNotFoundException("No user found with login: " + login);
        } else {
            return JwtAccountFactory.create(account.get());
        }
    }
}
