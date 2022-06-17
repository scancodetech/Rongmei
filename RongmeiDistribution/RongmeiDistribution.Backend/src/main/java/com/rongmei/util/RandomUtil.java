package com.rongmei.util;

import java.util.Date;
import java.util.Random;

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

  public static String generateShareCode() {
    int maxNum = 36;
    int i;
    int count = 0;
    char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    StringBuffer code = new StringBuffer();
    Random r = new Random();
    while (count < 10) {
      i = Math.abs(r.nextInt(maxNum));
      if (i >= 0 && i < str.length) {
        code.append(str[i]);
        count++;
      }
    }
    return code.toString();
  }
}
