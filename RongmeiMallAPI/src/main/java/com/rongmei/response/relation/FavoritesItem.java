package com.rongmei.response.relation;

public class FavoritesItem {

  private int id;
  private String name;
  private String username;
  private int orderId;
  private long createTime;
  private long updateTime;
  private boolean isActive;

  public FavoritesItem() {
  }

  public FavoritesItem(int id, String name, String username, int orderId, long createTime,
      long updateTime, boolean isActive) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.orderId = orderId;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
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
}
