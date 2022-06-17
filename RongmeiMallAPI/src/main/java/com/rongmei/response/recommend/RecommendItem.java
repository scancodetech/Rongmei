package com.rongmei.response.recommend;

import java.util.List;

public class RecommendItem {

  private long id;
  private int type;// 0:commodity 1:sale 2:blindbox 3:proposal 4:groupshopping 5: child
  private String title;
  private String description;
  private List<String> topics;
  private String coverUrl;
  private String ownerAvatarUrl;
  private String owner;
  private long createTime;
  private long updateTime;

  public RecommendItem() {
  }

  public RecommendItem(long id, int type, String title, String description,
      List<String> topics, String coverUrl, String ownerAvatarUrl, String owner, long createTime,
      long updateTime) {
    this.id = id;
    this.type = type;
    this.title = title;
    this.description = description;
    this.topics = topics;
    this.coverUrl = coverUrl;
    this.ownerAvatarUrl = ownerAvatarUrl;
    this.owner = owner;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getOwnerAvatarUrl() {
    return ownerAvatarUrl;
  }

  public void setOwnerAvatarUrl(String ownerAvatarUrl) {
    this.ownerAvatarUrl = ownerAvatarUrl;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
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
