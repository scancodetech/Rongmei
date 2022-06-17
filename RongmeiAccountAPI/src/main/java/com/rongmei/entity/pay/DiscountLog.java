package com.rongmei.entity.pay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discount_log")
public class DiscountLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "username")
  private String username;
  @Column(name = "amount")
  private long amount;
  @Column(name = "discount_type")
  private int discountType;//0:可提现积分 1:不可提现积分
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public DiscountLog() {
  }

  public DiscountLog(String username, long amount, int discountType, long createTime,
      long updateTime) {
    this.username = username;
    this.amount = amount;
    this.discountType = discountType;
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

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public int getDiscountType() {
    return discountType;
  }

  public void setDiscountType(int discountType) {
    this.discountType = discountType;
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
