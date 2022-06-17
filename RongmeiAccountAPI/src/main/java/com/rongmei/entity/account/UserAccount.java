package com.rongmei.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "large_coins")
  private long largeCoins;//可提现余额
  @Column(name = "disable_withdraw_coins", columnDefinition = "BIGINT default 0")
  private long disableWithDrawCoins;//限制场景余额
  @Column(name = "earnest_coins", columnDefinition = "BIGINT default 0")
  private long earnestCoins;//竞拍保证金
  @Column(name = "lottery_num", columnDefinition = "INT default 0")
  private int lotteryNum;//抽奖券

  public UserAccount() {
  }

  public UserAccount(int userId, long largeCoins, long disableWithDrawCoins, long earnestCoins,
      int lotteryNum) {
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
