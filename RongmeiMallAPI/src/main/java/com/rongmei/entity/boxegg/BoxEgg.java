package com.rongmei.entity.boxegg;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "box_egg")
public class BoxEgg {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "specification")
  private String specification;//套盒规格  1: 3*3 ， 2: 3*4；
  @Column(name = "cover_url")
  private String  coverUrl;//封面图路径
  @Column(name = "box_egg_name")
  private String  boxEggName;//盒蛋名称
  @Column(name = "introduction")
  private String boxEggIntroduction;//盒蛋简介

  @Column(name = "price")
  private int price;//盒蛋价格，
  // 现在盒蛋的价格有点疑问 9.9应该用long类型存，之前的price是用int存的

  @Column(name = "limit_number")
  private int limitNumber;//限量数
  @Column(name = "wallet_address")
  private String walletAddress;//钱包地址
  @Column(name = "resource_info")
  private String resourceInfo;//资源信息
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "status")
  private int status;//审核状态 1 未通过 2 通过  0 审核中

  public BoxEgg() {
  }

  public BoxEgg(String specification, String coverUrl, String boxEggName, String boxEggIntroduction, int limitNumber, String walletAddress, String resourceInfo, long createTime, long updateTime, int status) {
    this.specification = specification;
    this.coverUrl = coverUrl;
    this.boxEggName = boxEggName;
    this.boxEggIntroduction = boxEggIntroduction;
    this.limitNumber = limitNumber;
    this.walletAddress = walletAddress;
    this.resourceInfo = resourceInfo;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.status = status;
  }

  public BoxEgg(int id, String specification, String coverUrl, String boxEggName, String boxEggIntroduction, int limitNumber, String walletAddress, String resourceInfo, long createTime, long updateTime, int status) {
    this.id = id;
    this.specification = specification;
    this.coverUrl = coverUrl;
    this.boxEggName = boxEggName;
    this.boxEggIntroduction = boxEggIntroduction;
    this.limitNumber = limitNumber;
    this.walletAddress = walletAddress;
    this.resourceInfo = resourceInfo;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getBoxEggName() {
    return boxEggName;
  }

  public void setBoxEggName(String boxEggName) {
    this.boxEggName = boxEggName;
  }

  public String getBoxEggIntroduction() {
    return boxEggIntroduction;
  }

  public void setBoxEggIntroduction(String boxEggIntroduction) {
    this.boxEggIntroduction = boxEggIntroduction;
  }

  public int getLimitNumber() {
    return limitNumber;
  }

  public void setLimitNumber(int limitNumber) {
    this.limitNumber = limitNumber;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

  public void setWalletAddress(String walletAddress) {
    this.walletAddress = walletAddress;
  }

  public String getResourceInfo() {
    return resourceInfo;
  }

  public void setResourceInfo(String resourceInfo) {
    this.resourceInfo = resourceInfo;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
