package com.rongmei.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "order_id")
  private String orderId;
  @Column(name = "user_group_id")
  private int userGroupId;
  @Column(name = "large_price")
  private int largePrice;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "user_group_title")
  private String userGroupTitle;
  @Column(name = "page_url")
  private String pageUrl;
  @Column(name = "status")
  private String status;
  @Column(name = "total_num")
  private int totalNum;
  @Column(name = "complete_num")
  private int completeNum;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "customer")
  private String customer;
  @Column(name = "order_type", columnDefinition = "VARCHAR(255) DEFAULT 'group'")
  private String orderType;
  @Column(name = "relation_id")
  private int relationId;
  @Column(name = "favorites_id", columnDefinition = "BIGINT DEFAULT 0")
  private int favoritesId;

  public Order() {
  }

  public Order(String orderId, int userGroupId, int largePrice, String avatarUrl,
      String userGroupTitle, String pageUrl, String status, int totalNum, int completeNum,
      long createTime, String customer, String orderType, int relationId,
      int favoritesId) {
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
    this.customer = customer;
    this.orderType = orderType;
    this.relationId = relationId;
    this.favoritesId = favoritesId;
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

  public String getPageUrl() {
    return pageUrl;
  }

  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  public int getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(int userGroupId) {
    this.userGroupId = userGroupId;
  }

  public String getUserGroupTitle() {
    return userGroupTitle;
  }

  public void setUserGroupTitle(String userGroupTitle) {
    this.userGroupTitle = userGroupTitle;
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

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
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

  public int getFavoritesId() {
    return favoritesId;
  }

  public void setFavoritesId(int favoritesId) {
    this.favoritesId = favoritesId;
  }
}
