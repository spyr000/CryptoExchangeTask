package com.company.cryptoexchangetask.repo;

import com.company.cryptoexchangetask.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findBySecretKey(String secretKey);
}
