package com.rongmei.parameters.certification;

import com.rongmei.publicdatas.account.OuterPlatform;
import java.util.List;

public class UserMasterpieceUpdateParameters {

  private int id;
  private String certificationType;
  private String imageZipUrl;
  private String coverUrl;
  private String title;
  private String enTitle;
  private String pieceType;
  private String pieceStyle;
  private String pieceDate;
  private String description;
  private List<OuterPlatform> outerPlatforms;
  private String authorizationUrl;

  public UserMasterpieceUpdateParameters() {
  }

  public UserMasterpieceUpdateParameters(int id, String certificationType,
      String imageZipUrl, String coverUrl, String title, String enTitle, String pieceType,
      String pieceStyle, String pieceDate, String description,
      List<OuterPlatform> outerPlatforms, String authorizationUrl) {
    this.id = id;
    this.certificationType = certificationType;
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
}
