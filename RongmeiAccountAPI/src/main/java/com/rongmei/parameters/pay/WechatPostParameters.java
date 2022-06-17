package com.rongmei.parameters.pay;

public class WechatPostParameters {

  private int payType;//0: 手机 1: 网站
  private double totalAmount;
  private String ip;
  private String wechatAccount;

  public WechatPostParameters() {
  }

  public WechatPostParameters(int payType, double totalAmount, String ip,String wechatAccount) {
    this.payType = payType;
    this.totalAmount = totalAmount;
    this.ip = ip;
    this.wechatAccount = wechatAccount;
  }

  public String getWechatAccount() {
    return wechatAccount;
  }

  public void setWechatAccount(String wechatAccount) {
    this.wechatAccount = wechatAccount;
  }

  public int getPayType() {
    return payType;
  }

  public void setPayType(int payType) {
    this.payType = payType;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
}
