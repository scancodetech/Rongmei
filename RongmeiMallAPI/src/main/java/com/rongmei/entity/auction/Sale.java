package com.rongmei.entity.auction;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sale")
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "start_price")
  private double startPrice;
  @Column(name = "status")
  private String status; //拍卖中 已售出 流拍 已取消 咕咕
  //加价幅度
  @Column(name = "interval_price")
  private double intervalPrice;

  @Column(name = "thing_id")
  private int thingId;

  @Column(name = "create_time")
  private long createTime;

  @Column(name = "start_time")
  private long startTime;

  @Column(name = "end_time")
  private long endTime;


  @Column(name = "tags")
  @ElementCollection(targetClass = String.class)
  private List<String> tags = new ArrayList<>();
  //保证金
  @Column(name = "need_earnest_money", columnDefinition = "tinyint default 2")
  private boolean needEarnestMoney;


  @Column(name = "is_active", columnDefinition = "tinyint default 1")
  private boolean isActive;
  //是否截至
  @Column(name = "enable_intercept", columnDefinition = "tinyint default 2")
  private boolean enableIntercept;

  //是否版权
  @Column(name = "need_copyright_tax", columnDefinition = "tinyint default 2")
  private boolean needCopyrightTax;

  //最后价格
  @Column(name = "intercept_price", columnDefinition = "int default 0")
  private int interceptPrice;


  @Column(name = "rights")
  @ElementCollection(targetClass = String.class)
  private List<String> rights = new ArrayList<>();

  @Column(name = "draft_status", columnDefinition = "int default 0")
  private int draftStatus;//0:审核中 1:未通过 2:通过

  @Column(name = "type", columnDefinition = "tinyint(1) default 1")
  private int type;// 0 收入 1支出


  public Sale() {
  }

  public Sale(double startPrice, String status, double intervalPrice, int thingId, long createTime,
      long startTime, long endTime, List<String> tags, boolean needEarnestMoney, boolean isActive,
      boolean enableIntercept, boolean needCopyrightTax, int interceptPrice,
      List<String> rights, int draftStatus,int type) {

    this.startPrice = startPrice;
    this.status = status;
    this.intervalPrice = intervalPrice;
    this.thingId = thingId;
    this.createTime = createTime;
    this.startTime = startTime;
    this.endTime = endTime;
    this.tags = tags;
    this.needEarnestMoney = needEarnestMoney;
    this.isActive = isActive;
    this.enableIntercept = enableIntercept;
    this.needCopyrightTax = needCopyrightTax;
    this.interceptPrice = interceptPrice;
    this.rights = rights;
    this.draftStatus = draftStatus;
    this.type = type;
  }



  public boolean getNeedEarnestMoney(){
    return needEarnestMoney;
  }

  public boolean getNeedCopyrightTax(){
    return needCopyrightTax;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
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

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


}
