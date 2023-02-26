package com.company.cryptoexchangetask.controllers.auth;

import com.company.cryptoexchangetask.entities.user.User;
import com.company.cryptoexchangetask.repos.UserRepo;
import com.company.cryptoexchangetask.utils.AesEncrypter;
import com.company.cryptoexchangetask.utils.HexConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    @Autowired
    private AesEncrypter aesEncrypter;

    private final UserRepo userRepo;

    public DemoController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<String> sayHello() {
        User user = userRepo.findByUsername("noob").orElseThrow();
        String secretKey;
        try {
            secretKey = aesEncrypter.generateSecretKeyForUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user = aesEncrypter.recoverUserFromSecretKey(secretKey);

        return ResponseEntity.ok(user.getUsername() + " " + user.getEmail() + " " + user.getPassword());
    }
}
