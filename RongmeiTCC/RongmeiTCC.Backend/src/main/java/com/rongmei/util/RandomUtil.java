package com.rongmei.util;

import java.util.Date;

public class RandomUtil {
    public static final String ALLCHAR = "0123456789";

    public static String generateNonceStr() {
        return new Date().getTime() + "";
    }

    public static String generateCode(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            stringBuilder.append(ALLCHAR.charAt((int) Math.floor(Math.random() * ALLCHAR.length())));
        }
        return new String(stringBuilder);
    }
}
