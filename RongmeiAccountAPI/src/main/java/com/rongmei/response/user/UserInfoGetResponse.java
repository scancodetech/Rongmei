package com.rongmei.response.user;

import com.rongmei.response.Response;
import java.util.List;

public class UserInfoGetResponse extends Response {

  private int id;
  private String username;
  private String avatarUrl;
  private String nickname;
  private String email;
  private String address;
  private String description;
  private String personWebsite;
  private String wechat;
  private String weibo;
  private String intro;
  private String gender;
  private long birthday;
  private List<String> interests;

  public UserInfoGetResponse() {
  }

  public UserInfoGetResponse(int id, String username, String avatarUrl, String nickname,
      String email, String address, String description, String personWebsite, String wechat,
      String weibo, String intro, String gender, long birthday,
      List<String> interests) {
    this.id = id;
    this.username = username;
    this.avatarUrl = avatarUrl;
    this.nickname = nickname;
    this.email = email;
    this.address = address;
    this.description = description;
    this.personWebsite = personWebsite;
    this.wechat = wechat;
    this.weibo = weibo;
    this.intro = intro;
    this.gender = gender;
    this.birthday = birthday;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPersonWebsite() {
    return personWebsite;
  }

  public void setPersonWebsite(String personWebsite) {
    this.personWebsite = personWebsite;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getWeibo() {
    return weibo;
  }

  public void setWeibo(String weibo) {
    this.weibo = weibo;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public long getBirthday() {
    return birthday;
  }

  public void setBirthday(long birthday) {
    this.birthday = birthday;
  }

  public List<String> getInterests() {
    return interests;
  }

  public void setInterests(List<String> interests) {
    this.interests = interests;
  }
}
