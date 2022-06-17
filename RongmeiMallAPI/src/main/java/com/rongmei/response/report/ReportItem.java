package com.rongmei.response.report;

import java.util.List;

public class ReportItem {

  private int id;
  private String reason;
  private String description;
  private List<String> imageUrls;
  private String username;
  private long relationId;
  private long createTime;
  private long updateTime;

  public ReportItem(int id, String reason, String description,
      List<String> imageUrls, String username, long relationId, long createTime, long updateTime) {
    this.id = id;
    this.reason = reason;
    this.description = description;
    this.imageUrls = imageUrls;
    this.username = username;
    this.relationId = relationId;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getRelationId() {
    return relationId;
  }

  public void setRelationId(long relationId) {
    this.relationId = relationId;
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
