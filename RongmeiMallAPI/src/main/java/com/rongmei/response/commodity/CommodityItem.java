package com.rongmei.response.commodity;

import java.util.List;

public class CommodityItem {

  private int id;
  private String title;
  private String coverUrl;
  private long largePrice;
  private List<String> tags;
  private long createTime;
  private long updateTime;
  private boolean isExclusive;
  private String author;
  private String description;

  public CommodityItem() {
  }

  public CommodityItem(int id, String title, String coverUrl, long largePrice,
      List<String> tags, long createTime, long updateTime, boolean isExclusive,
      String author, String description) {
    this.id = id;
    this.title = title;
    this.coverUrl = coverUrl;
    this.largePrice = largePrice;
    this.tags = tags;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isExclusive = isExclusive;
    this.author = author;
    this.description = description;
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

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public long getLargePrice() {
    return largePrice;
  }

  public void setLargePrice(long largePrice) {
    this.largePrice = largePrice;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
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

  public boolean isExclusive() {
    return isExclusive;
  }

  public void setExclusive(boolean exclusive) {
    isExclusive = exclusive;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
