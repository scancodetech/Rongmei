package com.rongmei.response.platform;

import com.rongmei.response.Response;
import java.util.List;

public class PlatformDetailResponse extends Response {

  private int id;
  private String name;
  private String platformKey;
  private int status;
  private List<String> accessUsernames;

  public PlatformDetailResponse() {
  }

  public PlatformDetailResponse(int id, String name, String platformKey, int status,
      List<String> accessUsernames) {
    this.id = id;
    this.name = name;
    this.platformKey = platformKey;
    this.status = status;
    this.accessUsernames = accessUsernames;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlatformKey() {
    return platformKey;
  }

  public void setPlatformKey(String platformKey) {
    this.platformKey = platformKey;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public List<String> getAccessUsernames() {
    return accessUsernames;
  }

  public void setAccessUsernames(List<String> accessUsernames) {
    this.accessUsernames = accessUsernames;
  }
}
