package com.rongmei.entity.lottery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lottery_record")
public class LotteryRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "username")
  private String username;
  @Column(name = "name", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String name;
  @Column(name = "phone", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String phone;
  @Column(name = "email", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String email;
  @Column(name = "prize_type")
  private String prizeType;
  @Column(name = "prize_name")
  private String prizeName;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public LotteryRecord() {
  }

  public LotteryRecord(String username, String name, String phone, String email,
      String prizeType, String prizeName, long createTime, long updateTime) {
    this.username = username;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.prizeType = prizeType;
    this.prizeName = prizeName;
    this.createTime = createTime;
    this.updateTime = updateTime;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPrizeType() {
    return prizeType;
  }

  public void setPrizeType(String prizeType) {
    this.prizeType = prizeType;
  }

  public String getPrizeName() {
    return prizeName;
  }

  public void setPrizeName(String prizeName) {
    this.prizeName = prizeName;
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
