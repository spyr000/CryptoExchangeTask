package com.company.cryptoexchangetask.security;

import com.company.cryptoexchangetask.security.dto.AuthenticationRequest;
import com.company.cryptoexchangetask.security.dto.RegisterRequest;
import com.company.cryptoexchangetask.security.dto.AuthenticationResponse;
import com.company.cryptoexchangetask.security.AuthenticationService;
import com.company.cryptoexchangetask.security.dto.RegistrationResponse;
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
