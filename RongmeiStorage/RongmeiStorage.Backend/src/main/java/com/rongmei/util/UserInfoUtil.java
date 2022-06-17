package com.rongmei.util;

import com.rongmei.response.user.User;
import com.rongmei.response.user.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

public class UserInfoUtil {

  public static String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public static User getUser(String username) {
    String uri = "http://39.102.36.169:6789/account/entity?username=" + username;
    return (User) JSONObject.toBean(get(uri), User.class);
  }

  public static UserInfo getUserInfo(String username) {
    String uri = "http://39.102.36.169:6789/account/info/entity?username=" + username;
    return (UserInfo) JSONObject.toBean(get(uri), UserInfo.class);
  }

  private static JSONObject get(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    String strBody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
    return JSONObject.fromObject(strBody);
  }
}
