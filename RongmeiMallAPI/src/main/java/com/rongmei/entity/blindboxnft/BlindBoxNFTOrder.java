package com.rongmei.entity.blindboxnft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blind_box_order")
public class BlindBoxNFTOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "order_id")
  private String orderId;
  @Column(name = "price")
  private int price;
  @Column(name = "theme")
  private String theme;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "status")
  private String status;//queue,queue_failed,finished,failed
  @Column(name = "customer")
  private String customer;
  @Column(name = "blind_box_nft_id")
  private int blindBoxNftId;
  @Column(name = "favorites_id", columnDefinition = "BIGINT DEFAULT 0")
  private int favoritesId;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active")
  private boolean isActive;

  public BlindBoxNFTOrder(String orderId, int price, String theme,
      String avatarUrl, String status, String customer, int blindBoxNftId,
      int favoritesId, long createTime, long updateTime, boolean isActive) {
    this.orderId = orderId;
    this.price = price;
    this.theme = theme;
    this.avatarUrl = avatarUrl;
    this.status = status;
    this.customer = customer;
    this.blindBoxNftId = blindBoxNftId;
    this.favoritesId = favoritesId;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public int getBlindBoxNftId() {
    return blindBoxNftId;
  }

  public void setBlindBoxNftId(int blindBoxNftId) {
    this.blindBoxNftId = blindBoxNftId;
  }

  public int getFavoritesId() {
    return favoritesId;
  }

  public void setFavoritesId(int favoritesId) {
    this.favoritesId = favoritesId;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public BlindBoxNFTOrder() {

  }

}
