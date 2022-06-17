package com.rongmei.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {

  public static String SHA1Encode(String origin) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      byte[] bytes = md.digest(origin.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte aByte : bytes) {
        sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPassword;
  }

}
