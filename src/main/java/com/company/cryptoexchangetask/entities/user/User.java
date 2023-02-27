package com.company.cryptoexchangetask.entities.user;

import io.jsonwebtoken.io.Encoders;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
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


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", unique = true)
    @NotEmpty(message = "Name can not be empty")
    private String username;

    @Column(name = "email", unique = true)
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

    @Override
    public String getUsername() {
        return username;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
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
                Logger.getLogger(User.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return user;
    }
}
