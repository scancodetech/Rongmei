package com.rongmei.entity.pay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "withdraw_request")
public class WithdrawRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "status")
  private String status;
  @Column(name = "username")
  private String username;
  @Column(name = "total_amount")
  private double totalAmount;
  @Column(name = "alipay_account", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String alipayAccount;
  @Column(name = "wechat_account", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String wechatAccount;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public WithdrawRequest() {
  }

  public WithdrawRequest(String status, String username, double totalAmount,
      String alipayAccount, String wechatAccount, long createTime, long updateTime) {
    this.status = status;
    this.username = username;
    this.totalAmount = totalAmount;
    this.alipayAccount = alipayAccount;
    this.wechatAccount = wechatAccount;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getAlipayAccount() {
    return alipayAccount;
  }

  public void setAlipayAccount(String alipayAccount) {
    this.alipayAccount = alipayAccount;
  }

  public String getWechatAccount() {
    return wechatAccount;
  }

  public void setWechatAccount(String wechatAccount) {
    this.wechatAccount = wechatAccount;
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
