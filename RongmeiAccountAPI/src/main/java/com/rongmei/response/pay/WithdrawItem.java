package com.rongmei.response.pay;

import com.rongmei.entity.account.User;
import com.rongmei.entity.account.UserInfo;

public class WithdrawItem {

  private int id;
  private String status;
  private User user;
  private UserInfo userInfo;
  private String alipayAccount; //0 wechatpay 1alipay
  private String wecharAccount; //0 wechatpay 1alipay
  private double totalAmount;
  private long createTime;
  private long updateTime;

  public WithdrawItem() {
  }

  public WithdrawItem(int id, String status, User user, UserInfo userInfo, double totalAmount,
      long createTime, long updateTime,String alipayAccount,String wecharAccount) {
    this.id = id;
    this.status = status;
    this.user = user;
    this.userInfo = userInfo;
    this.totalAmount = totalAmount;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.alipayAccount = alipayAccount;
    this.wecharAccount = wecharAccount;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
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

  public String getAlipayAccount() {
    return alipayAccount;
  }

  public void setAlipayAccount(String alipayAccount) {
    this.alipayAccount = alipayAccount;
  }

  public String getWecharAccount() {
    return wecharAccount;
  }

  public void setWecharAccount(String wecharAccount) {
    this.wecharAccount = wecharAccount;
  }
}
