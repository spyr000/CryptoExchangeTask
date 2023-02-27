package com.company.cryptoexchangetask.controllers;

import com.company.cryptoexchangetask.repos.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    private final UserRepo userRepo;

    public DemoController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<String> sayHello() {

        List<String> uuidList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UUID u = UUID.randomUUID();
            uuidList.add(u.toString());
        }


        return ResponseEntity.ok(uuidList.toString());
    }
}
