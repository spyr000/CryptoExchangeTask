package com.company.cryptoexchangetask;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        var digestUtils = new DigestUtils("SHA3-256");
        for (int i = 0; i < 1000; i++) {
            System.out.println(digestUtils.digestAsHex(String.valueOf(i)).length());
        }
    }
}
