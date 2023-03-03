package com.company.cryptoexchangetask.controller.security;

import com.company.cryptoexchangetask.dto.security.AuthenticationRequest;
import com.company.cryptoexchangetask.dto.security.RegisterRequest;
import com.company.cryptoexchangetask.dto.security.AuthenticationResponse;
import com.company.cryptoexchangetask.dto.security.RegistrationResponse;
import com.company.cryptoexchangetask.service.security.AuthenticationService;
import com.company.cryptoexchangetask.service.security.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateRequest(
            @RequestBody AuthenticationRequest request
    ) {

        return ResponseEntity.ok(service.authenticate(request));
    }
}
