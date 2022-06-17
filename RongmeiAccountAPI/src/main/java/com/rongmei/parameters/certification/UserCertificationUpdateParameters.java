package com.rongmei.parameters.certification;

import com.rongmei.publicdatas.account.OuterPlatform;
import java.util.List;

public class UserCertificationUpdateParameters {

  private int id;
  private String certificationType;
  private String name;
  private List<String> userTypes;
  private String avatarUrl;
  private String position;
  private String email;
  private List<OuterPlatform> outerPlatforms;
  private String howToUse;
  private String howToKnow;
  private String gender;
  private String birthday;
  private String address;
  private String wechat;
  private String qq;
  private String description;

  public UserCertificationUpdateParameters() {
  }

  public UserCertificationUpdateParameters(int id, String certificationType, String name,
      List<String> userTypes, String avatarUrl, String position, String email,
      List<OuterPlatform> outerPlatforms, String howToUse, String howToKnow, String gender,
      String birthday, String address, String wechat, String qq, String description) {
    this.id = id;
    this.certificationType = certificationType;
    this.name = name;
    this.userTypes = userTypes;
    this.avatarUrl = avatarUrl;
    this.position = position;
    this.email = email;
    this.outerPlatforms = outerPlatforms;
    this.howToUse = howToUse;
    this.howToKnow = howToKnow;
    this.gender = gender;
    this.birthday = birthday;
    this.address = address;
    this.wechat = wechat;
    this.qq = qq;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCertificationType() {
    return certificationType;
  }

  public void setCertificationType(String certificationType) {
    this.certificationType = certificationType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getUserTypes() {
    return userTypes;
  }

  public void setUserTypes(List<String> userTypes) {
    this.userTypes = userTypes;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<OuterPlatform> getOuterPlatforms() {
    return outerPlatforms;
  }

  public void setOuterPlatforms(
      List<OuterPlatform> outerPlatforms) {
    this.outerPlatforms = outerPlatforms;
  }

  public String getHowToUse() {
    return howToUse;
  }

  public void setHowToUse(String howToUse) {
    this.howToUse = howToUse;
  }

  public String getHowToKnow() {
    return howToKnow;
  }

  public void setHowToKnow(String howToKnow) {
    this.howToKnow = howToKnow;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
