package com.rongmei.parameters.stats;

public class StatsCreateParameters {

  private String data;
  private String accessKey;
  private String accessSecret;

  public StatsCreateParameters() {
  }

  public StatsCreateParameters(String data, String accessKey, String accessSecret) {
    this.data = data;
    this.accessKey = accessKey;
    this.accessSecret = accessSecret;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
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
}
