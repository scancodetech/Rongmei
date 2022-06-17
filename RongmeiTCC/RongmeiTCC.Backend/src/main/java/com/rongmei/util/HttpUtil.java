package com.rongmei.util;

import com.rongmei.security.jwt.JwtEmployees;
import com.rongmei.security.jwt.JwtUser;
import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

  public static JSONObject get(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    String strBody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
    return JSONObject.fromObject(strBody);
  }
//  public static JwtEmployees getUserDetails(String uri) {
//    RestTemplate restTemplate = new RestTemplate();
//    return restTemplate.exchange(uri, HttpMethod.POST, null, JwtEmployees.class).getBody();
//  }
//  public static JwtUser getJwtUser(String uri) {
//    RestTemplate restTemplate = new RestTemplate();
//    return restTemplate.exchange(uri, HttpMethod.POST, null, JwtUser.class).getBody();
//  }
}
