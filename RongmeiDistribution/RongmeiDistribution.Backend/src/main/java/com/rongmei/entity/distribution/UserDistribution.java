package com.rongmei.entity.distribution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_distribution")
public class UserDistribution {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "username")
  private String username;
  @Column(name = "code")
  private String code;
  @Column(name = "qrcode_url")
  private String qrcodeUrl;
  @Column(name = "level")
  private int level;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public UserDistribution() {
  }

  public UserDistribution(int id, String username, String code, String qrcodeUrl, int level,
      long createTime, long updateTime) {
    this.id = id;
    this.username = username;
    this.code = code;
    this.qrcodeUrl = qrcodeUrl;
    this.level = level;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getQrcodeUrl() {
    return qrcodeUrl;
  }

  public void setQrcodeUrl(String qrcodeUrl) {
    this.qrcodeUrl = qrcodeUrl;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
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
