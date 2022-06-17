package com.rongmei.util;

import com.rongmei.exception.SystemException;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.User;
import com.rongmei.response.user.UserInfo;
import com.rongmei.response.user.UserInfoDetail;
import com.rongmei.response.user.UserItem;
import com.rongmei.response.usergroup.UserGroup;
import java.util.HashMap;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserInfoUtil {

  private static final LRUCache<String, UserInfo> userInfoCache = new LRUCache<>(100);

  public static UserInfo getUserInfoByCache(String username) {
    UserInfo userInfo;
    if (userInfoCache.containsKey(username)) {
      userInfo = userInfoCache.get(username);
    } else {
      try {
        userInfo = UserInfoUtil.getUserInfo(username);
      } catch (Exception e) {
        userInfo = new UserInfo();
      }
      userInfoCache.put(username, userInfo);
    }
    return userInfo;
  }

  public static String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public static User getUser(String username) {
    String baseServiceUrl =
        "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.api");
    String uri = baseServiceUrl + "/account/entity?username=" + username;
    return (User) JSONObject.toBean(HttpUtil.getJson(uri), User.class);
  }

  public static UserInfo getUserInfo(String username) {
    String baseServiceUrl =
        "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.api");
    String uri = baseServiceUrl + "/account/info/entity?username=" + username;
    return (UserInfo) JSONObject.toBean(HttpUtil.getJson(uri), UserInfo.class);
  }

  public static UserInfoDetail getUserInfoDetail(String username) {
    String baseServiceUrl =
        "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.api");
    String uri = baseServiceUrl + "/account/info/entity?username=" + username;
    UserInfo userInfo = new UserInfo();
    try {
      userInfo = (UserInfo) JSONObject.toBean(HttpUtil.getJson(uri), UserInfo.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new UserInfoDetail(userInfo.getId(), username, userInfo.getAvatarUrl(),
        userInfo.getNickname());
  }

  public static UserGroup getUserGroup(int userGroupId) {
    String baseServiceUrl =
        "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.api");
    String uri = baseServiceUrl + "/user_group/" + userGroupId;
    HashMap classMap = new HashMap();
    classMap.put("userItems", UserItem.class);
    try {
      return (UserGroup) JSONObject
          .toBean(HttpUtil.getJson(uri).getJSONObject("userGroupItem"), UserGroup.class, classMap);
    } catch (Exception e) {
      return new UserGroup();
    }
  }

  public static void discountEarnest(int totalAmount, String username)
      throws SystemException {
    String baseServiceUrl =
        "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.api");
    String uri =
        baseServiceUrl + "/pay/discount/earnest/inner?total_amount=" + totalAmount + "&username="
            + username;
    try {
      SuccessResponse response = (SuccessResponse) JSONObject
          .toBean(HttpUtil.getJson(uri), UserGroup.class);
      System.out.println(response.getDescription());
    } catch (Exception e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }

}
