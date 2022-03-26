package com.example.demo.util;

import java.security.SecureRandom;
import java.util.Base64;

public class CryptoHelper {
    public static String makeSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
