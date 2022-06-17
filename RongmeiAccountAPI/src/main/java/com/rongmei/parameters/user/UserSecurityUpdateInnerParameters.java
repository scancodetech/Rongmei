package com.rongmei.parameters.user;

public class UserSecurityUpdateInnerParameters {

  private String username;
  private String nearAccountId;
  private String nearPublicKey;
  private String nearPrivateKey;

  public UserSecurityUpdateInnerParameters() {
  }

  public UserSecurityUpdateInnerParameters(String username, String nearAccountId,
      String nearPublicKey, String nearPrivateKey) {
    this.username = username;
    this.nearAccountId = nearAccountId;
    this.nearPublicKey = nearPublicKey;
    this.nearPrivateKey = nearPrivateKey;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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
}
