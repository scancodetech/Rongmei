package com.rongmei.util;

import com.rongmei.response.auction.TokenResponse;
import com.rongmei.security.jwt.JwtEmployees;
import com.rongmei.security.jwt.JwtUser;
import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

  public static JSONObject getJson(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    String strBody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
    return JSONObject.fromObject(strBody);
  }

  public static String getString(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    String token = ThreadLocalUtil.getToken();
    headers.add("Authorization",token);
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
  }

  public static JwtEmployees getUserDetails(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    System.out.println(uri);
    return restTemplate.exchange(uri, HttpMethod.POST, null, JwtEmployees.class).getBody();
  }
  public static JwtUser getJwtUser(String uri) {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.exchange(uri, HttpMethod.POST, null, JwtUser.class).getBody();
  }

  public static String post(String uri, MultiValueMap<String, String> dataMap) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(dataMap, headers);
    return restTemplate.postForObject(uri, entity, String.class);
  }

  public static TokenResponse getTokenId(String uri, String json){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    String token = ThreadLocalUtil.getToken();
    headers.add("Authorization",token);

    HttpEntity<String> entity = new HttpEntity<>(json, headers);
    return restTemplate.postForObject(uri,entity,TokenResponse.class);
  }

  public static void postCasting(String uri,String json){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    String token = ThreadLocalUtil.getToken();
    headers.add("Authorization",token);
    HttpEntity<String> entity = new HttpEntity<>(json, headers);
    restTemplate.exchange(uri, HttpMethod.POST, entity,Object.class);
  }

}
