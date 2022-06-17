package com.rongmei.entity.child;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "simple_chlid")
public class SimpleChild {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "cover_url")
  private String coverUrl;
  @Column(name = "content")
  private String content;
  @Column(name = "topics")
  @ElementCollection(targetClass = String.class)
  private List<String> topics;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "author", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String author;
  @Column(name = "is_active")
  private boolean isActive;
  @Column(name = "draft_status", columnDefinition = "int default 0")
  private int draftStatus;//0:审核中 1:未通过 2:通过

  public SimpleChild() {
  }

  public SimpleChild(String coverUrl, String content, List<String> topics, long createTime,
      long updateTime, String author, boolean isActive, int draftStatus) {
    this.coverUrl = coverUrl;
    this.content = content;
    this.topics = topics;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.author = author;
    this.isActive = isActive;
    this.draftStatus = draftStatus;
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

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }
}
