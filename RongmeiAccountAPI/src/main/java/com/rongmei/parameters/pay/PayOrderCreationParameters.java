package com.rongmei.parameters.pay;

public class PayOrderCreationParameters {

  private double totalAmount;
  private String username;
  private String alipayAccount;
  private String wechatAccount;

  public PayOrderCreationParameters() {
  }

  public PayOrderCreationParameters(double totalAmount, String username,String alipayAccount,String wechatAccount) {
    this.totalAmount = totalAmount;
    this.username = username;
    this.alipayAccount = alipayAccount;
    this.wechatAccount = wechatAccount;
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

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
