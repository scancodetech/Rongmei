package com.rongmei.util;

import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.util.ResourceUtils;

public class PathUtil {

  public static String getTmpPath() {
    java.util.Properties properties = System.getProperties();
    String tempFileName = properties.getProperty("java.io.tmpdir");
    return tempFileName;
  }

  public static String getStaticPath() {
    return new File("/var/www/html/image/static/").getAbsolutePath() + "/";
  }

  public static String getResourcePath() throws FileNotFoundException {
    return ResourceUtils.getFile("classpath:assets").getAbsolutePath() + "/";
  }
}
