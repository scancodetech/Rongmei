package com.rongmei.entity.certification;

import com.rongmei.publicdatas.account.OuterPlatform;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_certification")
public class UserCertification {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "certification_type")
  private String certificationType;
  @Column(name = "username")
  private String username;
  @Column(name = "name")
  private String name;
  @Column(name = "user_types")
  @ElementCollection(targetClass = String.class)
  private List<String> userTypes;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "position")
  private String position;
  @Column(name = "email")
  private String email;
  @Column(name = "outer_platforms")
  @ElementCollection(targetClass = OuterPlatform.class)
  private List<OuterPlatform> outerPlatforms;
  @Column(name = "how_to_use")
  private String howToUse;
  @Column(name = "how_to_know")
  private String howToKnow;
  @Column(name = "gender")
  private String gender;
  @Column(name = "birthday")
  private String birthday;
  @Column(name = "address")
  private String address;
  @Column(name = "wechat")
  private String wechat;
  @Column(name = "qq")
  private String qq;
  @Column(name = "description")
  private String description;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "status", columnDefinition = "INT DEFAULT 0")
  private int status;//0:未审批 1:审批通过 2:审批不通过
  @Column(name = "approve_msg", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String approveMsg;

  public UserCertification() {
  }

  public UserCertification(String certificationType, String username, String name,
      List<String> userTypes, String avatarUrl, String position, String email,
      List<OuterPlatform> outerPlatforms, String howToUse, String howToKnow, String gender,
      String birthday, String address, String wechat, String qq, String description,
      long createTime, long updateTime, int status, String approveMsg) {
    this.certificationType = certificationType;
    this.username = username;
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
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.status = status;
    this.approveMsg = approveMsg;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getApproveMsg() {
    return approveMsg;
  }

  public void setApproveMsg(String approveMsg) {
    this.approveMsg = approveMsg;
  }
}
