package com.rongmei.response.user;

import com.rongmei.response.Response;

public class UserBasisSecurityGetResponse extends Response {

  private int id;
  private int userId;
  private String nearAccountId;

  public UserBasisSecurityGetResponse() {
  }

  public UserBasisSecurityGetResponse(int id, int userId, String nearAccountId) {
    this.id = id;
    this.userId = userId;
    this.nearAccountId = nearAccountId;
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
}
