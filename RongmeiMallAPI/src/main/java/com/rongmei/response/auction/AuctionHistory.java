package com.rongmei.response.auction;

import com.rongmei.response.user.UserInfoDetail;

public class AuctionHistory {

  private int id;
  private double price;
  private UserInfoDetail user;
  private long createTime;

  public AuctionHistory() {
  }

  public AuctionHistory(int id, double price, UserInfoDetail user, long createTime) {
    this.id = id;
    this.price = price;
    this.user = user;
    this.createTime = createTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public UserInfoDetail getUser() {
    return user;
  }

  public void setUser(UserInfoDetail user) {
    this.user = user;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
