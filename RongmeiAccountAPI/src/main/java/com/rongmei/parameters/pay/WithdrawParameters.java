package com.rongmei.parameters.pay;

public class WithdrawParameters {
  private String username;
  private double coins;
  private String alipayAccount;
  private String wechatAccount;

  public WithdrawParameters() {
  }

  public WithdrawParameters(String username, double coins, String alipayAccount,
      String wechatAccount) {
    this.username = username;
    this.coins = coins;
    this.alipayAccount = alipayAccount;
    this.wechatAccount = wechatAccount;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public double getCoins() {
    return coins;
  }

  public void setCoins(double coins) {
    this.coins = coins;
  }

  public String getAlipayAccount() {
    return alipayAccount;
  }

  public void setAlipayAccount(String alipayAccount) {
    this.alipayAccount = alipayAccount;
  }

  public String getWechatAccount() {
    return wechatAccount;
  }

  public void setWechatAccount(String wechatAccount) {
    this.wechatAccount = wechatAccount;
  }
}
