package com.rongmei.response.user;

import java.util.List;

public class UserInfo {

  private int id;
  private String avatarUrl;
  private String nickname;
  private List<String> interests;

  public UserInfo() {
  }

  public UserInfo(int id, String avatarUrl, String nickname,
      List<String> interests) {
    this.id = id;
    this.avatarUrl = avatarUrl;
    this.nickname = nickname;
    this.interests = interests;
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

  public List<String> getInterests() {
    return interests;
  }

  public void setInterests(List<String> interests) {
    this.interests = interests;
  }
}
