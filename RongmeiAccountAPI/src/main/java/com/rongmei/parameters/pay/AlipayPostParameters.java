package com.rongmei.parameters.pay;

public class AlipayPostParameters {

  private int payType;//0: 手机 1: 网站
  private double totalAmount;
  private String alipayAccount;
  private String returnUrl;

  public AlipayPostParameters() {
  }

  public String getAlipayAccount() {
    return alipayAccount;
  }

  public void setAlipayAccount(String alipayAccount) {
    this.alipayAccount = alipayAccount;
  }


  public AlipayPostParameters(int payType, double totalAmount, String returnUrl, String alipayAccount) {
    this.payType = payType;
    this.totalAmount = totalAmount;
    this.returnUrl = returnUrl;
    this.alipayAccount = alipayAccount;

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

  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }
}
