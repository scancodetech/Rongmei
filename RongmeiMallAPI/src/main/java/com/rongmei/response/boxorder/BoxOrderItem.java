package com.rongmei.response.boxorder;

public class BoxOrderItem {

  private int id;

  private int orderType;
  private String cooperateType;
  private String customType;
  private String customStyle;
  private String worldOutlook;
  private String customTheme;
  private String elementRequirements;
  private int status;
  private String exampleUrl;
  private int price;
  private String username;
  private long createTime;
  private long updateTime;
  private long acceptTime;
  private String resultCoverUrl;
  private String resultUrl;
  private int commentStatus;
  private String comment;
  private long finishTime;

  public BoxOrderItem() {
  }



  public BoxOrderItem(int id, int orderType, String cooperateType, String customType,
      String customStyle, String worldOutlook, String customTheme,
      String elementRequirements, int status, String exampleUrl, int price, String username,
      long createTime, long updateTime, long acceptTime, String resultCoverUrl,
      String resultUrl, int commentStatus, String comment, long finishTime) {
    this.id = id;
    this.customType = customType;
    this.customStyle = customStyle;
    this.orderType = orderType;
    this.cooperateType = cooperateType;
    this.customType = customType;
    this.customStyle = customStyle;
    this.worldOutlook = worldOutlook;
    this.customTheme = customTheme;
    this.elementRequirements = elementRequirements;
    this.status = status;
    this.exampleUrl = exampleUrl;
    this.price = price;
    this.username = username;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.acceptTime = acceptTime;
    this.resultCoverUrl = resultCoverUrl;
    this.resultUrl = resultUrl;
    this.commentStatus = commentStatus;
    this.comment = comment;
    this.finishTime = finishTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getOrderType() {
    return orderType;
  }

  public void setOrderType(int orderType) {
    this.orderType = orderType;
  }

  public String getCooperateType() {
    return cooperateType;
  }

  public void setCooperateType(String cooperateType) {
    this.cooperateType = cooperateType;
  }

  public String getWorldOutlook() {
    return worldOutlook;
  }

  public void setWorldOutlook(String worldOutlook) {
    this.worldOutlook = worldOutlook;
  }

  public String getCustomType() {
    return customType;
  }

  public void setCustomType(String customType) {
    this.customType = customType;
  }

  public String getCustomStyle() {
    return customStyle;
  }

  public void setCustomStyle(String customStyle) {
    this.customStyle = customStyle;
  }

  public String getCustomTheme() {
    return customTheme;
  }

  public void setCustomTheme(String customTheme) {
    this.customTheme = customTheme;
  }

  public String getElementRequirements() {
    return elementRequirements;
  }

  public void setElementRequirements(String elementRequirements) {
    this.elementRequirements = elementRequirements;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getExampleUrl() {
    return exampleUrl;
  }

  public void setExampleUrl(String exampleUrl) {
    this.exampleUrl = exampleUrl;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
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

  public long getAcceptTime() {
    return acceptTime;
  }

  public void setAcceptTime(long acceptTime) {
    this.acceptTime = acceptTime;
  }

  public String getResultCoverUrl() {
    return resultCoverUrl;
  }

  public void setResultCoverUrl(String resultCoverUrl) {
    this.resultCoverUrl = resultCoverUrl;
  }

  public String getResultUrl() {
    return resultUrl;
  }

  public void setResultUrl(String resultUrl) {
    this.resultUrl = resultUrl;
  }

  public int getCommentStatus() {
    return commentStatus;
  }

  public void setCommentStatus(int commentStatus) {
    this.commentStatus = commentStatus;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(long finishTime) {
    this.finishTime = finishTime;
  }
}
