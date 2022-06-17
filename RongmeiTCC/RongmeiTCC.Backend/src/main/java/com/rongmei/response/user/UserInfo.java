package com.rongmei.response.user;

public class UserInfo {

  private int id;
  private String avatarUrl;
  private String nickname;

  public UserInfo() {
  }

  public UserInfo(int id, String avatarUrl, String nickname) {
    this.id = id;
    this.avatarUrl = avatarUrl;
    this.nickname = nickname;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}
