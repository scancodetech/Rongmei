package com.rongmei.entity.access;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access")
public class Access {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "access_key")
  private String accessKey;
  @Column(name = "access_secret")
  private String accessSecret;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "status")
  private int status;

  public Access() {
  }

  public Access(String name, String accessKey, String accessSecret, long createTime, int status) {
    this.name = name;
    this.accessKey = accessKey;
    this.accessSecret = accessSecret;
    this.createTime = createTime;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getAccessSecret() {
    return accessSecret;
  }

  public void setAccessSecret(String accessSecret) {
    this.accessSecret = accessSecret;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
