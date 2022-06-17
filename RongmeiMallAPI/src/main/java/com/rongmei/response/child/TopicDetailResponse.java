package com.rongmei.response.child;

import com.rongmei.response.Response;

public class TopicDetailResponse extends Response {

  private int id;
  private String topic;
  private String coverUrl;
  private String description;
  private long createTime;
  private long updateTime;

  public TopicDetailResponse() {
  }

  public TopicDetailResponse(int id, String topic, String coverUrl, String description,
      long createTime, long updateTime) {
    this.id = id;
    this.topic = topic;
    this.coverUrl = coverUrl;
    this.description = description;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
