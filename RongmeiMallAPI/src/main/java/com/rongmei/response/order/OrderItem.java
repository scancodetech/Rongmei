package com.rongmei.response.order;

public class OrderItem {

  private int id;
  private String orderId;
  private int userGroupId;
  private int largePrice;
  private String avatarUrl;
  private String userGroupTitle;
  private String pageUrl;
  private String status;
  private int totalNum;
  private int completeNum;
  private long createTime;
  private String orderType;
  private int relationId;

  public OrderItem() {
  }

  public OrderItem(int id, String orderId, int userGroupId, int largePrice,
      String avatarUrl, String userGroupTitle, String pageUrl, String status, int totalNum,
      int completeNum, long createTime, String orderType, int relationId) {
    this.id = id;
    this.orderId = orderId;
    this.userGroupId = userGroupId;
    this.largePrice = largePrice;
    this.avatarUrl = avatarUrl;
    this.userGroupTitle = userGroupTitle;
    this.pageUrl = pageUrl;
    this.status = status;
    this.totalNum = totalNum;
    this.completeNum = completeNum;
    this.createTime = createTime;
    this.orderType = orderType;
    this.relationId = relationId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(int userGroupId) {
    this.userGroupId = userGroupId;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public int getLargePrice() {
    return largePrice;
  }

  public void setLargePrice(int largePrice) {
    this.largePrice = largePrice;
  }

  public String getUserGroupTitle() {
    return userGroupTitle;
  }

  public void setUserGroupTitle(String userGroupTitle) {
    this.userGroupTitle = userGroupTitle;
  }

  public String getPageUrl() {
    return pageUrl;
  }

  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(int totalNum) {
    this.totalNum = totalNum;
  }

  public int getCompleteNum() {
    return completeNum;
  }

  public void setCompleteNum(int completeNum) {
    this.completeNum = completeNum;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }
}
