package com.rongmei.paramaters.distribution;

public class UserDistributionUpdateParameters {
  private String username;
  private int level;

  public UserDistributionUpdateParameters() {
  }

  public UserDistributionUpdateParameters(String username, int level) {
    this.username = username;
    this.level = level;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
