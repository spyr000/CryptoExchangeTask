package com.company.cryptoexchangetask.dto.security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String password;
    private String username;
}
