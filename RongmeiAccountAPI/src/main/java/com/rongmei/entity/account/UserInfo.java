package com.rongmei.entity.account;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_info")
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "nickname")
  private String nickname;
  @Column(name = "email", columnDefinition = "VARCHAR(255) default ''")
  private String email;
  @Column(name = "address", columnDefinition = "VARCHAR(255) default ''")
  private String address;
  @Column(name = "description", columnDefinition = "VARCHAR(1023) default ''")
  private String description;
  @Column(name = "person_website", columnDefinition = "VARCHAR(255) default ''")
  private String personWebsite;
  @Column(name = "wechat", columnDefinition = "VARCHAR(255) default ''")
  private String wechat;
  @Column(name = "weibo", columnDefinition = "VARCHAR(255) default ''")
  private String weibo;
  @Column(name = "intro", columnDefinition = "VARCHAR(255) default ''")
  private String intro;
  @Column(name = "gender", columnDefinition = "VARCHAR(255) default ''")
  private String gender;//男 女 机器人 未知
  @Column(name = "birthday", columnDefinition = "BIGINT default 0")
  private long birthday;

  public UserInfo() {
  }

  public UserInfo(int userId, String avatarUrl, String nickname, String email,
      String address, String description, String personWebsite, String wechat, String weibo,
      String intro, String gender, long birthday) {
    this.userId = userId;
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
}
