package com.rongmei.response.access;

public class AccessItem {

  private int id;
  private String name;
  private String accessKey;
  private String accessSecret;
  private int status;

  public AccessItem() {
  }

  public AccessItem(int id, String name, String accessKey, String accessSecret, int status) {
    this.id = id;
    this.name = name;
    this.accessKey = accessKey;
    this.accessSecret = accessSecret;
    this.status = status;
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

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getAccessSecret() {
    return accessSecret;
  }

  public void setAccessSecret(String accessSecret) {
    this.accessSecret = accessSecret;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
