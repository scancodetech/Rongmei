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
@Table(name = "user_masterpiece")
public class UserMasterpiece {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "certification_type")
  private String certificationType;
  @Column(name = "username")
  private String username;
  @Column(name = "image_zip_url")
  private String imageZipUrl;
  @Column(name = "cover_url")
  private String coverUrl;
  @Column(name = "title")
  private String title;
  @Column(name = "en_title")
  private String enTitle;
  @Column(name = "piece_type")
  private String pieceType;
  @Column(name = "piece_style")
  private String pieceStyle;
  @Column(name = "piece_date")
  private String pieceDate;
  @Column(name = "description")
  private String description;
  @Column(name = "outer_platforms")
  @ElementCollection(targetClass = OuterPlatform.class)
  private List<OuterPlatform> outerPlatforms;
  @Column(name = "authorization_url")
  private String authorizationUrl;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "status", columnDefinition = "INT DEFAULT 0")
  private int status;//0:未审批 1:审批通过 2:审批不通过
  @Column(name = "approve_msg", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String approveMsg;

  public UserMasterpiece() {
  }


  public UserMasterpiece(String certificationType, String username, String imageZipUrl,
      String coverUrl, String title, String enTitle, String pieceType, String pieceStyle,
      String pieceDate, String description,
      List<OuterPlatform> outerPlatforms, String authorizationUrl, long createTime, long updateTime,
      int status, String approveMsg) {
    this.certificationType = certificationType;
    this.username = username;
    this.imageZipUrl = imageZipUrl;
    this.coverUrl = coverUrl;
    this.title = title;
    this.enTitle = enTitle;
    this.pieceType = pieceType;
    this.pieceStyle = pieceStyle;
    this.pieceDate = pieceDate;
    this.description = description;
    this.outerPlatforms = outerPlatforms;
    this.authorizationUrl = authorizationUrl;
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

  public String getImageZipUrl() {
    return imageZipUrl;
  }

  public void setImageZipUrl(String imageZipUrl) {
    this.imageZipUrl = imageZipUrl;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEnTitle() {
    return enTitle;
  }

  public void setEnTitle(String enTitle) {
    this.enTitle = enTitle;
  }

  public String getPieceType() {
    return pieceType;
  }

  public void setPieceType(String pieceType) {
    this.pieceType = pieceType;
  }

  public String getPieceStyle() {
    return pieceStyle;
  }

  public void setPieceStyle(String pieceStyle) {
    this.pieceStyle = pieceStyle;
  }

  public String getPieceDate() {
    return pieceDate;
  }

  public void setPieceDate(String pieceDate) {
    this.pieceDate = pieceDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<OuterPlatform> getOuterPlatforms() {
    return outerPlatforms;
  }

  public void setOuterPlatforms(
      List<OuterPlatform> outerPlatforms) {
    this.outerPlatforms = outerPlatforms;
  }

  public String getAuthorizationUrl() {
    return authorizationUrl;
  }

  public void setAuthorizationUrl(String authorizationUrl) {
    this.authorizationUrl = authorizationUrl;
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
