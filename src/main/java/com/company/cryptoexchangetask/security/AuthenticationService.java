package com.company.cryptoexchangetask.security;

import com.company.cryptoexchangetask.entities.user.Role;
import com.company.cryptoexchangetask.entities.user.User;
import com.company.cryptoexchangetask.repos.UserRepo;
import com.company.cryptoexchangetask.security.dto.AuthenticationRequest;
import com.company.cryptoexchangetask.security.dto.RegisterRequest;
import com.company.cryptoexchangetask.security.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getUsername(),
                Role.USER
        );
        userRepo.save(user);
        String token = jwtService.generateToken(UserDetailsImpl.fromUser(user));
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(UserDetailsImpl.fromUser(user));
        return new AuthenticationResponse(token);
    }
}
