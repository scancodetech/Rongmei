package com.rongmei.response.user;

public class UserInfoDetail {

  private int id;
  private String username;
  private String avatarUrl;
  private String nickname;

  public UserInfoDetail() {
  }

  public UserInfoDetail(int id, String username, String avatarUrl, String nickname) {
    this.id = id;
    this.username = username;
    this.avatarUrl = avatarUrl;
    this.nickname = nickname;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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
