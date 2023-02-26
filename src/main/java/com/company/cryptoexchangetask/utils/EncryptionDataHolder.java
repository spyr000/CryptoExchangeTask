package com.company.cryptoexchangetask.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EncryptionDataHolder {
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Value("${security.encryption.password}")
    private String password;
    @Value("${security.encryption.key}")
    private String key;

    public EncryptionDataHolder() {
    }
}
