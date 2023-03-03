package com.company.cryptoexchangetask.service.impl;

import com.company.cryptoexchangetask.entity.user.User;
import com.company.cryptoexchangetask.repo.UserRepo;
import com.company.cryptoexchangetask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User getUser(String secretKey) {
        return userRepo.findBySecretKey(secretKey)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
