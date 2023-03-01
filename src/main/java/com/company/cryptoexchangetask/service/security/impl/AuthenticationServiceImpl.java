package com.company.cryptoexchangetask.service.security.impl;

import com.company.cryptoexchangetask.entity.user.Role;
import com.company.cryptoexchangetask.entity.user.User;
import com.company.cryptoexchangetask.exception.BaseException;
import com.company.cryptoexchangetask.repo.UserRepo;
import com.company.cryptoexchangetask.dto.security.AuthenticationRequest;
import com.company.cryptoexchangetask.dto.security.RegisterRequest;
import com.company.cryptoexchangetask.dto.security.AuthenticationResponse;
import com.company.cryptoexchangetask.dto.security.RegistrationResponse;
import com.company.cryptoexchangetask.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JwtServiceImpl jwtServiceImpl;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegistrationResponse register(RegisterRequest request) {
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getUsername(),
                Role.USER
        );
        userRepo.save(user);

        return new RegistrationResponse(user.getSecretKey());
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtServiceImpl.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
