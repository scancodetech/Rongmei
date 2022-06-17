package com.rongmei.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_interest")
public class UserInterest {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "username")
  private String username;
  @Column(name = "tag")
  private String tag;
  @Column(name = "create_time")
  private long createTime;

  public UserInterest() {
  }

  public UserInterest(int userId, String username, String tag, long createTime) {
    this.userId = userId;
    this.username = username;
    this.tag = tag;
    this.createTime = createTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
