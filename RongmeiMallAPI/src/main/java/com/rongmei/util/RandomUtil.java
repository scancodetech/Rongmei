package com.rongmei.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

  public static long generateNum(int num) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(System.currentTimeMillis());
    for (int i = 0; i < num; i++) {
      stringBuilder.append(ALLCHAR.charAt((int) Math.floor(Math.random() * ALLCHAR.length())));
    }
    return Long.parseLong(new String(stringBuilder));
  }

  public static String generateNum2String(int num) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(System.currentTimeMillis());
    for (int i = 0; i < num; i++) {
      stringBuilder.append(ALLCHAR.charAt((int) Math.floor(Math.random() * ALLCHAR.length())));
    }
    return (new String(stringBuilder));
  }

  public static void main(String[] args) {
    System.out.println(generateNum2String(10));
  }
}
