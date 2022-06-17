package com.rongmei.parameters.user;

public class UserAccountUpdateParameters {

  private String username;
  private int largeCoins;

  public UserAccountUpdateParameters() {
  }

  public UserAccountUpdateParameters(String username, int largeCoins) {
    this.username = username;
    this.largeCoins = largeCoins;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getLargeCoins() {
    return largeCoins;
  }

  public void setLargeCoins(int largeCoins) {
    this.largeCoins = largeCoins;
  }
}
