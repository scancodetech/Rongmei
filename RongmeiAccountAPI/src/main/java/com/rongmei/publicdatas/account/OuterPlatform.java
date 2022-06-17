package com.rongmei.publicdatas.account;

import javax.persistence.Embeddable;

@Embeddable
public class OuterPlatform {

  private String platform;
  private String userInfo;

  public OuterPlatform() {
  }

  public OuterPlatform(String platform, String userInfo) {
    this.platform = platform;
    this.userInfo = userInfo;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(String userInfo) {
    this.userInfo = userInfo;
  }
}
