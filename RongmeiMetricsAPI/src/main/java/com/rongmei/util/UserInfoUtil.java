package com.rongmei.util;

import com.rongmei.response.user.User;
import com.rongmei.response.user.UserInfo;
import com.rongmei.response.user.UserInfoDetail;
import com.rongmei.response.user.UserItem;
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
    return (User) JSONObject.toBean(HttpUtil.get(uri), User.class);
  }

  public static UserInfo getUserInfo(String username) {
    String uri = "http://39.102.36.169:6789/account/info/entity?username=" + username;
    return (UserInfo) JSONObject.toBean(HttpUtil.get(uri), UserInfo.class);
  }

  public static UserInfoDetail getUserInfoDetail(String username) {
    String uri = "http://39.102.36.169:6789/account/info/entity?username=" + username;
    UserInfo userInfo = new UserInfo();
    try {
      userInfo = (UserInfo) JSONObject.toBean(HttpUtil.get(uri), UserInfo.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new UserInfoDetail(userInfo.getId(), username, userInfo.getAvatarUrl(),
        userInfo.getNickname());
  }

}
