package com.rongmei.parameters.user;

public class UserSecurityUpdateParameters {
  private int id;
  private int userId;
  private String nearAccountId;
  private String nearPublicKey;
  private String nearPrivateKey;
  private String payNum;
  private String captcha;

  public UserSecurityUpdateParameters() {
  }

  public UserSecurityUpdateParameters(int id, int userId, String nearAccountId,
      String nearPublicKey, String nearPrivateKey, String payNum, String captcha) {
    this.id = id;
    this.userId = userId;
    this.nearAccountId = nearAccountId;
    this.nearPublicKey = nearPublicKey;
    this.nearPrivateKey = nearPrivateKey;
    this.payNum = payNum;
    this.captcha = captcha;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getNearAccountId() {
    return nearAccountId;
  }

  public void setNearAccountId(String nearAccountId) {
    this.nearAccountId = nearAccountId;
  }

  public String getNearPublicKey() {
    return nearPublicKey;
  }

  public void setNearPublicKey(String nearPublicKey) {
    this.nearPublicKey = nearPublicKey;
  }

  public String getNearPrivateKey() {
    return nearPrivateKey;
  }

  public void setNearPrivateKey(String nearPrivateKey) {
    this.nearPrivateKey = nearPrivateKey;
  }

  public String getPayNum() {
    return payNum;
  }

  public void setPayNum(String payNum) {
    this.payNum = payNum;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }
}
