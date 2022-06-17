package com.rongmei.response.blindbox;

public class BlindBoxOrderItem {

  private long id;
  private String username;
  private long blindBoxId;
  private int price;
  private String theme;
  private String classification;
  private String status;
  private long createTime;
  private long updateTime;

  public BlindBoxOrderItem() {
  }

  public BlindBoxOrderItem(long id, String username, long blindBoxId, int price,
      String theme, String classification, String status, long createTime, long updateTime) {
    this.id = id;
    this.username = username;
    this.blindBoxId = blindBoxId;
    this.price = price;
    this.theme = theme;
    this.classification = classification;
    this.status = status;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getBlindBoxId() {
    return blindBoxId;
  }

  public void setBlindBoxId(long blindBoxId) {
    this.blindBoxId = blindBoxId;
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

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
}
