package com.github.MicroBlog.repository;

import com.github.MicroBlog.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByLogin (String login);
    Optional <Account> findById (Long id);
    Optional <Account> findByUnigueAccName (String uniqueAccName);
}
