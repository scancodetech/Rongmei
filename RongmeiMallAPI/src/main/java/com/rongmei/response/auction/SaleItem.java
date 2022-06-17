package com.rongmei.response.auction;

import com.rongmei.entity.auction.Thing;
import java.util.List;

public class SaleItem {

  private int id;
  private double startPrice;
  private String status;
  private double intervalPrice;
  private double currentPrice;
  private Thing thing;
  private long createTime;
  private long startTime;
  private long endTime;
  private List<String> tags;
  private boolean needEarnestMoney;

  public SaleItem() {
  }

  public SaleItem(int id, double startPrice, String status, double intervalPrice,
      double currentPrice,
      Thing thing, long createTime, long startTime, long endTime,
      List<String> tags, boolean needEarnestMoney) {
    this.id = id;
    this.startPrice = startPrice;
    this.status = status;
    this.intervalPrice = intervalPrice;
    this.currentPrice = currentPrice;
    this.thing = thing;
    this.createTime = createTime;
    this.startTime = startTime;
    this.endTime = endTime;
    this.tags = tags;
    this.needEarnestMoney = needEarnestMoney;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getStartPrice() {
    return startPrice;
  }

  public void setStartPrice(double startPrice) {
    this.startPrice = startPrice;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getIntervalPrice() {
    return intervalPrice;
  }

  public void setIntervalPrice(double intervalPrice) {
    this.intervalPrice = intervalPrice;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(double currentPrice) {
    this.currentPrice = currentPrice;
  }

  public Thing getThing() {
    return thing;
  }

  public void setThing(Thing thing) {
    this.thing = thing;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public boolean isNeedEarnestMoney() {
    return needEarnestMoney;
  }

  public void setNeedEarnestMoney(boolean needEarnestMoney) {
    this.needEarnestMoney = needEarnestMoney;
  }
}
