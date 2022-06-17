package com.rongmei.response.auction;

import com.rongmei.entity.auction.Thing;
import com.rongmei.response.Response;
import com.rongmei.response.user.UserInfo;
import java.util.List;

public class SaleDetailResponse extends Response {

  private int id;
  private double startPrice;
  private String status;
  private double intervalPrice;
  private Thing thing;
  private long createTime;
  private long startTime;
  private long endTime;
  private double currPrice;
  private double minePrice;
  private UserInfo author;
  private UserInfo owner;
  private List<String> tags;
  private boolean needEarnestMoney;
  private boolean enableIntercept;
  private boolean needCopyrightTax;
  private int interceptPrice;
  private List<String> rights;
  private int likeNum;

  public SaleDetailResponse() {
  }

  public SaleDetailResponse(int id, double startPrice, String status, double intervalPrice,
      Thing thing, long createTime, long startTime, long endTime, double currPrice,
      double minePrice,
      UserInfo author, UserInfo owner, List<String> tags, boolean needEarnestMoney,
      boolean enableIntercept, boolean needCopyrightTax, int interceptPrice,
      List<String> rights, int likeNum) {
    this.id = id;
    this.startPrice = startPrice;
    this.status = status;
    this.intervalPrice = intervalPrice;
    this.thing = thing;
    this.createTime = createTime;
    this.startTime = startTime;
    this.endTime = endTime;
    this.currPrice = currPrice;
    this.minePrice = minePrice;
    this.author = author;
    this.owner = owner;
    this.tags = tags;
    this.needEarnestMoney = needEarnestMoney;
    this.enableIntercept = enableIntercept;
    this.needCopyrightTax = needCopyrightTax;
    this.interceptPrice = interceptPrice;
    this.rights = rights;
    this.likeNum = likeNum;
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

  public Thing getThing() {
    return thing;
  }

  public void setThing(Thing thing) {
    this.thing = thing;
  }

  public double getIntervalPrice() {
    return intervalPrice;
  }

  public void setIntervalPrice(double intervalPrice) {
    this.intervalPrice = intervalPrice;
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

  public double getCurrPrice() {
    return currPrice;
  }

  public void setCurrPrice(double currPrice) {
    this.currPrice = currPrice;
  }

  public double getMinePrice() {
    return minePrice;
  }

  public void setMinePrice(double minePrice) {
    this.minePrice = minePrice;
  }

  public UserInfo getAuthor() {
    return author;
  }

  public void setAuthor(UserInfo author) {
    this.author = author;
  }

  public UserInfo getOwner() {
    return owner;
  }

  public void setOwner(UserInfo owner) {
    this.owner = owner;
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

  public boolean isEnableIntercept() {
    return enableIntercept;
  }

  public void setEnableIntercept(boolean enableIntercept) {
    this.enableIntercept = enableIntercept;
  }

  public boolean isNeedCopyrightTax() {
    return needCopyrightTax;
  }

  public void setNeedCopyrightTax(boolean needCopyrightTax) {
    this.needCopyrightTax = needCopyrightTax;
  }

  public int getInterceptPrice() {
    return interceptPrice;
  }

  public void setInterceptPrice(int interceptPrice) {
    this.interceptPrice = interceptPrice;
  }

  public List<String> getRights() {
    return rights;
  }

  public void setRights(List<String> rights) {
    this.rights = rights;
  }

  public int getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(int likeNum) {
    this.likeNum = likeNum;
  }
}
