package com.talend.tuj.generator.utils;

import java.security.SecureRandom;

public class JobID {
    public static String generateJobID() {
        String easy = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder buffer = new StringBuilder("_");
        for (int i = 0; i < 22; i++) {
            buffer.append(easy.charAt(random.nextInt(easy.length())));
        }
        return buffer.toString();
    }
}
