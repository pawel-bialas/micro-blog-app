package com.github.MicroBlog.security;

import com.github.MicroBlog.model.User;
import com.github.MicroBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> account = userRepository.findByLogin(login);
        if (!account.isPresent()) {
            throw new UsernameNotFoundException("No user found with login: " + login);
        } else {
            return JwtAccountFactory.create(account.get());
        }
    }
}
