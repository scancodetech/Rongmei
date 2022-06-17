package com.rongmei.paramaters.distribution;

public class DistributionParameters {
  private String code;
  private String username;

  public DistributionParameters() {
  }

  public DistributionParameters(String code, String username) {
    this.code = code;
    this.username = username;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
