package com.company.cryptoexchangetask.utils;

import com.company.cryptoexchangetask.config.ApplicationConfig;
import com.company.cryptoexchangetask.entities.user.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class AesEncrypter {
    @Value("${security.encryption.password}")
    private String password;
    @Value("${security.encryption.key}")
    private String key;

    public AesEncrypter() {
    }

    private String encrypt(String message) {
        return Encryptors.text(password, key).encrypt(message);
    }

    private String decrypt(String cipher) {
        return Encryptors.text(password, key).decrypt(cipher);
    }

    public String generateSecretKeyForUser(User user) throws IOException {

        String userHex = HexConverter.bytesToHex(user.toByteArray());
        return encrypt(userHex);
    }

    public User recoverUserFromSecretKey(String secretKey) {
        String userHex = decrypt(secretKey);
        return User.fromBytes(HexConverter.hexTobyteArray(userHex));
    }
}
