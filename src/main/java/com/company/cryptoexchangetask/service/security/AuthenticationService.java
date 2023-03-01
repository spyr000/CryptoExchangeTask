package com.company.cryptoexchangetask.service.security;

import com.company.cryptoexchangetask.dto.security.AuthenticationRequest;
import com.company.cryptoexchangetask.dto.security.AuthenticationResponse;
import com.company.cryptoexchangetask.dto.security.RegisterRequest;
import com.company.cryptoexchangetask.dto.security.RegistrationResponse;

public interface AuthenticationService {
    RegistrationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
