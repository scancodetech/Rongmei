package com.rongmei.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_security")
public class UserSecurity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "near_account_id", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String nearAccountId;
  @Column(name = "near_public_key", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String nearPublicKey;
  @Column(name = "near_private_key", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String nearPrivateKey;
  @Column(name = "pay_num")
  private String payNum;
  @Column(name = "create_time", columnDefinition = "bigint DEFAULT 0")
  private long createTime;
  @Column(name = "update_time", columnDefinition = "bigint DEFAULT 0")
  private long updateTime;

  public UserSecurity() {
  }

  public UserSecurity(int userId, String nearAccountId, String nearPublicKey,
      String nearPrivateKey, String payNum, long createTime, long updateTime) {
    this.userId = userId;
    this.nearAccountId = nearAccountId;
    this.nearPublicKey = nearPublicKey;
    this.nearPrivateKey = nearPrivateKey;
    this.payNum = payNum;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getNearAccountId() {
    return nearAccountId;
  }

  public void setNearAccountId(String nearAccountId) {
    this.nearAccountId = nearAccountId;
  }

  public String getNearPublicKey() {
    return nearPublicKey;
  }

  public void setNearPublicKey(String nearPublicKey) {
    this.nearPublicKey = nearPublicKey;
  }

  public String getNearPrivateKey() {
    return nearPrivateKey;
  }

  public void setNearPrivateKey(String nearPrivateKey) {
    this.nearPrivateKey = nearPrivateKey;
  }

  public String getPayNum() {
    return payNum;
  }

  public void setPayNum(String payNum) {
    this.payNum = payNum;
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
