package com.company.cryptoexchangetask.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User implements Serializable {
    @Builder
    public User(String email,
                String password,
                String username,
                Role role
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.secretKey = new DigestUtils("SHA3-256").digestAsHex(username);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email is not valid")
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 22, message = "Password length should be between 8 an 22 characters")
    private String password;

    @Column(name = "secret_key", unique = true, nullable = false)
    private String secretKey;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;
}
