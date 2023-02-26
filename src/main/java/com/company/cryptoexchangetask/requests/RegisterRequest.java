package com.company.cryptoexchangetask.requests;

import java.time.LocalDate;

public class RegisterRequest {
    public RegisterRequest(String username, String email, String password, LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegisterRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;

    private String email;

    private String password;
}
