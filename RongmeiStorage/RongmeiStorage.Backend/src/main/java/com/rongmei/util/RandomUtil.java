package com.rongmei.util;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

  public static final String ALLCHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

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

  public static String genKey(int length) {
    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*:_+<>?~#$@";
    Random random = new Random();
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(base.length());
      buffer.append(base.charAt(number));
    }
    return new String(buffer);
  }
}
