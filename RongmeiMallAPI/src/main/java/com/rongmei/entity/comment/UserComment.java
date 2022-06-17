package com.rongmei.entity.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_comment")
public class UserComment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "username")
  private String username;
  @Column(name = "content")
  private String content;
  @Column(name = "to_username")
  private String toUsername;
  @Column(name = "top_comment_id", columnDefinition = "BIGINT default 0")
  private long topCommentId;
  @Column(name = "to_comment_id")
  private long toCommentId;
  @Column(name = "relation_id")
  private int relationId;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active", columnDefinition = "tinyint DEFAULT 2")
  private boolean isActive;//not used

  public UserComment() {
  }

  public UserComment(String username, String content, String toUsername, long topCommentId,
      long toCommentId, int relationId, long createTime, long updateTime, boolean isActive) {
    this.username = username;
    this.content = content;
    this.toUsername = toUsername;
    this.topCommentId = topCommentId;
    this.toCommentId = toCommentId;
    this.relationId = relationId;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
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

  public String getToUsername() {
    return toUsername;
  }

  public void setToUsername(String toUsername) {
    this.toUsername = toUsername;
  }

  public long getToCommentId() {
    return toCommentId;
  }

  public void setToCommentId(long toCommentId) {
    this.toCommentId = toCommentId;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getTopCommentId() {
    return topCommentId;
  }

  public void setTopCommentId(long topCommentId) {
    this.topCommentId = topCommentId;
  }
}
