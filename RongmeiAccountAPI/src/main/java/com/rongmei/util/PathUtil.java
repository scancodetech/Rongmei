package com.rongmei.util;

import java.io.File;

public class PathUtil {

  public static String getTmpPath() {
    java.util.Properties properties = System.getProperties();
    return properties.getProperty("java.io.tmpdir");
  }

  public static String getStaticPath() {
    return new File("/var/www/html/image/static/").getAbsolutePath() + "/";
  }

}
