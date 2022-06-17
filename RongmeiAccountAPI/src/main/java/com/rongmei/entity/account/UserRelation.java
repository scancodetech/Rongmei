package com.rongmei.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_relation")
public class UserRelation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "from_username")
  private String fromUsername;
  @Column(name = "to_username")
  private String toUsername;
  @Column(name = "relation_type")
  private int relationType;//1:关注
  @Column(name = "create_time")
  private long createTime;

  public UserRelation() {
  }

  public UserRelation(String fromUsername, String toUsername, int relationType, long createTime) {
    this.fromUsername = fromUsername;
    this.toUsername = toUsername;
    this.relationType = relationType;
    this.createTime = createTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFromUsername() {
    return fromUsername;
  }

  public void setFromUsername(String fromUsername) {
    this.fromUsername = fromUsername;
  }

  public String getToUsername() {
    return toUsername;
  }

  public void setToUsername(String toUsername) {
    this.toUsername = toUsername;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public int getRelationType() {
    return relationType;
  }

  public void setRelationType(int relationType) {
    this.relationType = relationType;
  }
}
