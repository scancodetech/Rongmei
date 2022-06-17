package com.rongmei.parameters.boxegg;

public class UpdateCastBoxEggParameters {
  private int id;

  private String specification;//套盒规格  1: 3*3 ， 2: 3*4；

  private String  coverUrl;//封面图路径

  private String  boxEggName;//盒蛋名称

  private String boxEggIntroduction;//盒蛋简介

  private int limitNumber;//限量数

  private String walletAddress;//钱包地址

  private String resourceInfo;//资源信息

//  private String status;//审核状态

//  private file resourceInfo;//资源信息



  public UpdateCastBoxEggParameters() {
  }

  public UpdateCastBoxEggParameters(int id, String specification, String coverUrl, String boxEggName, String boxEggIntroduction, int limitNumber, String walletAddress, String resourceInfo) {
    this.id = id;
    this.specification = specification;
    this.coverUrl = coverUrl;
    this.boxEggName = boxEggName;
    this.boxEggIntroduction = boxEggIntroduction;
    this.limitNumber = limitNumber;
    this.walletAddress = walletAddress;
    this.resourceInfo = resourceInfo;
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


}
