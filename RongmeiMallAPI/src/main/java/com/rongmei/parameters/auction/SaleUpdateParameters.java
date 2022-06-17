package com.rongmei.parameters.auction;

import java.util.ArrayList;
import java.util.List;

public class SaleUpdateParameters {

  private int id;
  private double startPrice;
  private String status;
  private int thingId;
  private double intervalPrice;
  private long startTime;
  private long endTime;
  private List<String> tags;
  private boolean needEarnestMoney;
  private boolean enableIntercept;
  private boolean needCopyrightTax;
  private int interceptPrice;
  private List<String> rights;
  private int type;


  public SaleUpdateParameters() {
  }

  public SaleUpdateParameters(int id, double startPrice, String status, int thingId,
      double intervalPrice, long startTime, long endTime, List<String> tags,
      boolean needEarnestMoney,
      boolean enableIntercept, boolean needCopyrightTax, int interceptPrice,
      List<String> rights,int type) {
    this.id = id;
    this.startPrice = startPrice;
    this.status = status;
    this.thingId = thingId;
    this.intervalPrice = intervalPrice;
    this.startTime = startTime;
    this.endTime = endTime;
    this.tags = tags;
    this.needEarnestMoney = needEarnestMoney;
    this.enableIntercept = enableIntercept;
    this.needCopyrightTax = needCopyrightTax;
    this.interceptPrice = interceptPrice;
    this.rights = rights;
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
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

  public int getThingId() {
    return thingId;
  }

  public void setThingId(int thingId) {
    this.thingId = thingId;
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
}
