package com.rongmei.entity.distribution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "distribution")
public class Distribution {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "from_username")
  private String fromUsername;
  @Column(name = "to_username")
  private String toUsername;
  @Column(name = "create_time")
  private long createTime;

  public Distribution() {
  }

  public Distribution(int id, String fromUsername, String toUsername, long createTime) {
    this.id = id;
    this.fromUsername = fromUsername;
    this.toUsername = toUsername;
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
}
