package com.rongmei.parameters.pay;

import java.util.Date;

public class AlipayNotifyParameters {
  private Date notifyTime;
  private String notifyType;
  private String notifyId;
  private String appId;
  private String signType;
  private String sign;
  private String tradeNo;
  private String outTradeNo;
  private String buyerLogonId;
  private String sellerId;
  private String sellerEmail;
  private double totalAmount;

  public AlipayNotifyParameters() {
  }

  public AlipayNotifyParameters(Date notifyTime, String notifyType, String notifyId,
      String appId, String signType, String sign, String tradeNo, String outTradeNo,
      String buyerLogonId, String sellerId, String sellerEmail, double totalAmount) {
    this.notifyTime = notifyTime;
    this.notifyType = notifyType;
    this.notifyId = notifyId;
    this.appId = appId;
    this.signType = signType;
    this.sign = sign;
    this.tradeNo = tradeNo;
    this.outTradeNo = outTradeNo;
    this.buyerLogonId = buyerLogonId;
    this.sellerId = sellerId;
    this.sellerEmail = sellerEmail;
    this.totalAmount = totalAmount;
  }

  public Date getNotifyTime() {
    return notifyTime;
  }

  public void setNotifyTime(Date notifyTime) {
    this.notifyTime = notifyTime;
  }

  public String getNotifyType() {
    return notifyType;
  }

  public void setNotifyType(String notifyType) {
    this.notifyType = notifyType;
  }

  public String getNotifyId() {
    return notifyId;
  }

  public void setNotifyId(String notifyId) {
    this.notifyId = notifyId;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public String getBuyerLogonId() {
    return buyerLogonId;
  }

  public void setBuyerLogonId(String buyerLogonId) {
    this.buyerLogonId = buyerLogonId;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public String getSellerEmail() {
    return sellerEmail;
  }

  public void setSellerEmail(String sellerEmail) {
    this.sellerEmail = sellerEmail;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }
}
