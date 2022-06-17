package com.rongmei.util;

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
}
