package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.entity.user.User;

public interface UserService {
    User getUser(String username);
}
