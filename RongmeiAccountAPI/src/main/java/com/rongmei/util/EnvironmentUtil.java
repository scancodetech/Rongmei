package com.rongmei.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {
  private static String springProfilesActive;

  @Value(value = "${spring.profiles.active}")
  public void setSpringProfilesActive(String springProfilesActive) {
    EnvironmentUtil.springProfilesActive = springProfilesActive;
  }

  public static String getSpringProfilesActive() {
    return springProfilesActive;
  }

  public static String packEnvironment(String service) {
    String[] services = service.split("\\.");
    StringBuilder result = new StringBuilder();
    for (String serviceName : services) {
      result.append(serviceName).append("_");
    }
    result.append(springProfilesActive);
    return result.toString();
  }

  public static String getLocalHostPath(){
    return "https://api.dimension.pub/" + packEnvironment("rongmei.account");
  }
}
