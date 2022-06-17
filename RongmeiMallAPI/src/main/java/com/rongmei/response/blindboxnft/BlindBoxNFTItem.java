package com.rongmei.response.blindboxnft;

public class BlindBoxNFTItem {

  private int id;
  private String name;
  private String url;
  private int price;
  private String description;
  private String theme;
  private String classification;
  private long createTime;
  private long updateTime;
  private boolean isActive;

  public BlindBoxNFTItem() {
  }

  public BlindBoxNFTItem(int id, String name, String url, int price, String description,
      String theme, String classification, long createTime, long updateTime, boolean isActive) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.price = price;
    this.description = description;
    this.theme = theme;
    this.classification = classification;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
