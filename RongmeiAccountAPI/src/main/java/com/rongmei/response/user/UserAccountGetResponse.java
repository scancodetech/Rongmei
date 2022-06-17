package com.rongmei.response.user;

import com.rongmei.response.Response;

public class UserAccountGetResponse extends Response {

  private int id;
  private int userId;
  private long largeCoins;
  private long disableWithDrawCoins;
  private long earnestCoins;
  private int lotteryNum;

  public UserAccountGetResponse() {
  }

  public UserAccountGetResponse(int id, int userId, long largeCoins, long disableWithDrawCoins,
      long earnestCoins, int lotteryNum) {
    this.id = id;
    this.userId = userId;
    this.largeCoins = largeCoins;
    this.disableWithDrawCoins = disableWithDrawCoins;
    this.earnestCoins = earnestCoins;
    this.lotteryNum = lotteryNum;
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

  public long getLargeCoins() {
    return largeCoins;
  }

  public void setLargeCoins(long largeCoins) {
    this.largeCoins = largeCoins;
  }

  public long getDisableWithDrawCoins() {
    return disableWithDrawCoins;
  }

  public void setDisableWithDrawCoins(long disableWithDrawCoins) {
    this.disableWithDrawCoins = disableWithDrawCoins;
  }

  public long getEarnestCoins() {
    return earnestCoins;
  }

  public void setEarnestCoins(long earnestCoins) {
    this.earnestCoins = earnestCoins;
  }

  public int getLotteryNum() {
    return lotteryNum;
  }

  public void setLotteryNum(int lotteryNum) {
    this.lotteryNum = lotteryNum;
  }
}
