package com.rongmei.entity.account;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group_application")
public class UserGroupApplication {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "title")
  private String title;
  @Column(name = "signature", columnDefinition = "VARCHAR(1024)")
  private String signature;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "org_type")
  private int orgType;//1:国家机构 2:媒体 3:其他
  @Column(name = "org_name")
  private String orgName;
  @Column(name = "org_certification")
  private String orgCertification;
  @Column(name = "business_license_url")
  private String businessLicenseUrl;
  @Column(name = "official_letter_url")
  private String officialLetterUrl;
  @Column(name = "other_file_url")
  private String otherFileUrl;
  @Column(name = "operator_name")
  private String operatorName;
  @Column(name = "operator_identity_card")
  private String operatorIdentityCard;
  @Column(name = "operator_phone")
  private String operatorPhone;
  @Column(name = "username")
  private String username;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_approve")
  private boolean isApprove;
  @Column(name = "approve_msg", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String approveMsg;

  public UserGroupApplication() {
  }

  public UserGroupApplication(String title, String signature, String avatarUrl, int orgType,
      String orgName, String orgCertification, String businessLicenseUrl,
      String officialLetterUrl, String otherFileUrl, String operatorName,
      String operatorIdentityCard, String operatorPhone, String username, long createTime,
      long updateTime, boolean isApprove, String approveMsg) {
    this.title = title;
    this.signature = signature;
    this.avatarUrl = avatarUrl;
    this.orgType = orgType;
    this.orgName = orgName;
    this.orgCertification = orgCertification;
    this.businessLicenseUrl = businessLicenseUrl;
    this.officialLetterUrl = officialLetterUrl;
    this.otherFileUrl = otherFileUrl;
    this.operatorName = operatorName;
    this.operatorIdentityCard = operatorIdentityCard;
    this.operatorPhone = operatorPhone;
    this.username = username;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isApprove = isApprove;
    this.approveMsg = approveMsg;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public int getOrgType() {
    return orgType;
  }

  public void setOrgType(int orgType) {
    this.orgType = orgType;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getOrgCertification() {
    return orgCertification;
  }

  public void setOrgCertification(String orgCertification) {
    this.orgCertification = orgCertification;
  }

  public String getBusinessLicenseUrl() {
    return businessLicenseUrl;
  }

  public void setBusinessLicenseUrl(String businessLicenseUrl) {
    this.businessLicenseUrl = businessLicenseUrl;
  }

  public String getOfficialLetterUrl() {
    return officialLetterUrl;
  }

  public void setOfficialLetterUrl(String officialLetterUrl) {
    this.officialLetterUrl = officialLetterUrl;
  }

  public String getOtherFileUrl() {
    return otherFileUrl;
  }

  public void setOtherFileUrl(String otherFileUrl) {
    this.otherFileUrl = otherFileUrl;
  }

  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public String getOperatorIdentityCard() {
    return operatorIdentityCard;
  }

  public void setOperatorIdentityCard(String operatorIdentityCard) {
    this.operatorIdentityCard = operatorIdentityCard;
  }

  public String getOperatorPhone() {
    return operatorPhone;
  }

  public void setOperatorPhone(String operatorPhone) {
    this.operatorPhone = operatorPhone;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public boolean isApprove() {
    return isApprove;
  }

  public void setApprove(boolean approve) {
    isApprove = approve;
  }

  public String isApproveMsg() {
    return approveMsg;
  }

  public String getApproveMsg() {
    return approveMsg;
  }

  public void setApproveMsg(String approveMsg) {
    this.approveMsg = approveMsg;
  }
}
