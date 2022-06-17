package com.rongmei.util;

import com.rongmei.response.user.User;
import com.rongmei.response.user.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserInfoUtil {

  private static String springProfilesActive;

  @Value(value = "${spring.profiles.active}")
  public void setSpringProfilesActive(String springProfilesActive) {
    UserInfoUtil.springProfilesActive = springProfilesActive;
  }

  public static String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public static User getUser(String username) {
    String baseServiceUrl = "https://api.dimension.pub/" + packEnvironment("rongmei.account.api",
        springProfilesActive);
    String uri = baseServiceUrl + "/account/entity?username=" + username;
    return (User) JSONObject.toBean(HttpUtil.getJson(uri), User.class);
  }

  public static UserInfo getUserInfo(String username) {
    String baseServiceUrl = "https://api.dimension.pub/" + packEnvironment("rongmei.account.api",
        springProfilesActive);
    String uri = baseServiceUrl + "/account/info/entity?username=" + username;
    return (UserInfo) JSONObject.toBean(HttpUtil.getJson(uri), UserInfo.class);
  }

  public static String packEnvironment(String service, String environment) {
    String[] services = service.split("\\.");
    StringBuilder result = new StringBuilder();
    for (String serviceName : services) {
      result.append(serviceName).append("_");
    }
    result.append(environment);
    return result.toString();
  }
}
