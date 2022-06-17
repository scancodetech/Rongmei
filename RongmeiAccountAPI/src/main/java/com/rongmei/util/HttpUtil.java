package com.rongmei.util;

import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
  }

  public static String post(String uri, MultiValueMap<String, String> dataMap) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(dataMap, headers);
    return restTemplate.postForObject(uri, entity, String.class);
  }

  public static void put(String uri,String json){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.add("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjIyMjY4MDAzMTAsImF1dGhvcml0aWVzIjpbeyJpdGVtTmFtZSI6IkhPTUVQQUdFIiwiYXV0aG9yaXR5IjoiSE9NRVBBR0UifSx7Iml0ZW1OYW1lIjoiQVVUSE9SSVRZIiwiYXV0aG9yaXR5IjoiQVVUSE9SSVRZIn0seyJpdGVtTmFtZSI6IlJPTEUiLCJhdXRob3JpdHkiOiJST0xFIn0seyJpdGVtTmFtZSI6Ik1BVEVSSUFMU09VUkNFIiwiYXV0aG9yaXR5IjoiTUFURVJJQUxTT1VSQ0UifSx7Iml0ZW1OYW1lIjoiRU1QTE9ZRUVTIiwiYXV0aG9yaXR5IjoiRU1QTE9ZRUVTIn0seyJpdGVtTmFtZSI6IkdST1VQIiwiYXV0aG9yaXR5IjoiR1JPVVAifSx7Iml0ZW1OYW1lIjoiRklOQU5DSUFMIiwiYXV0aG9yaXR5IjoiRklOQU5DSUFMIn0seyJpdGVtTmFtZSI6IlJFQ0hBUkdFIiwiYXV0aG9yaXR5IjoiUkVDSEFSR0UifSx7Iml0ZW1OYW1lIjoiV0lUSERSQVdBTCIsImF1dGhvcml0eSI6IldJVEhEUkFXQUwifSx7Iml0ZW1OYW1lIjoiQ0hFQ0siLCJhdXRob3JpdHkiOiJDSEVDSyJ9LHsiaXRlbU5hbWUiOiJQQVJUSVRJT04iLCJhdXRob3JpdHkiOiJQQVJUSVRJT04ifSx7Iml0ZW1OYW1lIjoiQVVUSE9SIiwiYXV0aG9yaXR5IjoiQVVUSE9SIn0seyJpdGVtTmFtZSI6Ik1BU1RFUlBJRUNFIiwiYXV0aG9yaXR5IjoiTUFTVEVSUElFQ0UifSx7Iml0ZW1OYW1lIjoiQ09OVEVOVCIsImF1dGhvcml0eSI6IkNPTlRFTlQifSx7Iml0ZW1OYW1lIjoiTUFURVJJQUwiLCJhdXRob3JpdHkiOiJNQVRFUklBTCJ9LHsiaXRlbU5hbWUiOiJMT1RTIiwiYXV0aG9yaXR5IjoiTE9UUyJ9LHsiaXRlbU5hbWUiOiJCT1hFR0ciLCJhdXRob3JpdHkiOiJCT1hFR0cifSx7Iml0ZW1OYW1lIjoiR0lBTlRDQVIiLCJhdXRob3JpdHkiOiJHSUFOVENBUiJ9LHsiaXRlbU5hbWUiOiJQUk9QT1NBTCIsImF1dGhvcml0eSI6IlBST1BPU0FMIn0seyJpdGVtTmFtZSI6IlBVQkxJU0giLCJhdXRob3JpdHkiOiJQVUJMSVNIIn0seyJpdGVtTmFtZSI6IkVYQU0iLCJhdXRob3JpdHkiOiJFWEFNIn0seyJpdGVtTmFtZSI6IlRPUElDIiwiYXV0aG9yaXR5IjoiVE9QSUMifSx7Iml0ZW1OYW1lIjoiVE9QSUNDSEVDSyIsImF1dGhvcml0eSI6IlRPUElDQ0hFQ0sifSx7Iml0ZW1OYW1lIjoiVEhFTUUiLCJhdXRob3JpdHkiOiJUSEVNRSJ9LHsiaXRlbU5hbWUiOiJSRVBPUlQiLCJhdXRob3JpdHkiOiJSRVBPUlQifSx7Iml0ZW1OYW1lIjoiQUJPVVQiLCJhdXRob3JpdHkiOiJBQk9VVCJ9LHsiaXRlbU5hbWUiOiJIT01FUEFHRSIsImF1dGhvcml0eSI6IkhPTUVQQUdFIn1dLCJ1c2VybmFtZSI6ImFkbWluIn0.hYHA9qhbA3c589xiFCBrT39Sc78e66FzdQcPWc3Z89HubMLGUx2SIts_I5srRGxZskfHS5uQWoJ3-yyR9z50mg");
    HttpEntity<String> entity = new HttpEntity<>(json, headers);
    restTemplate.put(uri,entity);
  }
}
