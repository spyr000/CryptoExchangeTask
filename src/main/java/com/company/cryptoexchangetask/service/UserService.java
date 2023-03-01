package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.entity.user.User;
import com.company.cryptoexchangetask.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public User getUser(String username) {
        return userRepo.findBySecretKey(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserByKey(String key) {
        return userRepo.findBySecretKey(key).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
