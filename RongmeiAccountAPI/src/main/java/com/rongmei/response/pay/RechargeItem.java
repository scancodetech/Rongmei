package com.rongmei.response.pay;

import com.rongmei.entity.account.User;
import com.rongmei.entity.account.UserInfo;

public class RechargeItem {

  private String id;
  private String status;
  private User user;
  private UserInfo userInfo;
  private double totalAmount;
  private String alipayAccount;
  private String wechatAccount;
  private long createTime;
  private long updateTime;
  private int payType;

  public RechargeItem() {
  }

  public RechargeItem(String id, String status, User user,
      UserInfo userInfo, double totalAmount, long createTime, long updateTime, int payType,
                      String alipayAccount,String wechatAccount) {
    this.id = id;
    this.status = status;
    this.user = user;
    this.userInfo = userInfo;
    this.totalAmount = totalAmount;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.payType = payType;
    this.alipayAccount = alipayAccount;
    this.wechatAccount = wechatAccount;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public int getPayType() {
    return payType;
  }

  public void setPayType(int payType) {
    this.payType = payType;
  }
}
