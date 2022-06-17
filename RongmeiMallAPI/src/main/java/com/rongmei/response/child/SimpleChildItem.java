package com.rongmei.response.child;

import java.util.List;

public class SimpleChildItem {

  private int id;
  private String coverUrl;
  private String content;
  private List<String> topics;
  private long createTime;
  private long updateTime;
  private String author;
  private boolean isActive;

  public SimpleChildItem() {
  }

  public SimpleChildItem(int id, String coverUrl, String content,
      List<String> topics, long createTime, long updateTime, String author, boolean isActive) {
    this.id = id;
    this.coverUrl = coverUrl;
    this.content = content;
    this.topics = topics;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.author = author;
    this.isActive = isActive;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
