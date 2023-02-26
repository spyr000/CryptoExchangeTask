package com.company.cryptoexchangetask.entities.user;

import io.jsonwebtoken.io.Encoders;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name", "email"})})
public class User implements Serializable, UserDetails {

    public User(String email,
                String password,
                String username,
                Role role
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
//        this.secretKey = Encoders.BASE64.encode(username.getBytes())
//                + Encoders.BASE64.encode(email.getBytes())
//                + Encoders.BASE64.encode(password.getBytes())
//                +
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    @NotEmpty(message = "Name can not be empty")
    private String username;

    @Column(name = "email")
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email is not valid")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password can not be empty")
    @Size(min = 8, max = 22, message = "Password length should be between 8 an 22 characters")
    private String password;

//    @Column(name = "secret_key", unique = true)
//    @NotEmpty
//    private String secretKey;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        byte[] outputBytes;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            outputBytes = byteArrayOutputStream.toByteArray();
        } finally {
            byteArrayOutputStream.close();
        }
        return outputBytes;
    }

    public static User fromBytes(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInput objectInput = null;
        User user;
        try {
            objectInput = new ObjectInputStream(byteArrayInputStream);
            user = (User) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (objectInput != null) {
                    objectInput.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.WARNING,ex.getMessage(),ex);
            }
        }
        return user;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
