package com.company.cryptoexchangetask.utils;


import org.apache.commons.codec.binary.Hex;

import java.util.HexFormat;

public class HexConverter {
    public static String bytesToHex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static byte[] hexTobyteArray(String hex) {
        return HexFormat.of().parseHex(hex);
    }
}
