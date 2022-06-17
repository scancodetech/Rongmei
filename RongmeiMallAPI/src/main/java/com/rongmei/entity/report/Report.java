package com.rongmei.entity.report;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "report")
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "reason")
  private String reason;
  @Column(name = "description")
  private String description;
  @Column(name = "imageUrls")
  @ElementCollection(targetClass = String.class)
  private List<String> imageUrls;
  @Column(name = "username")
  private String username;
  @Column(name = "relation_id")
  private long relationId;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public Report() {
  }

  public Report(String reason, String description, List<String> imageUrls,
      String username, long relationId, long createTime, long updateTime) {
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
