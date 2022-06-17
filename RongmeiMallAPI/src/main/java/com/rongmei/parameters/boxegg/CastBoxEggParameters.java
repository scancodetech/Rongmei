package com.rongmei.parameters.boxegg;

import java.util.List;

public class CastBoxEggParameters {


  private String specification;//套盒规格  1: 3*3 ， 2: 3*4；

  private int boxNumber;//箱数

  private  CastBoxEggItem hideBoxEgg;//隐藏款

  private List<CastBoxEggItem> commonBoxEggList;//普通款

  private String walletAddress;//钱包地址

  private String  seriesName;//系列名称

  private String seriesIntroduction;//系列简介

//  private String status;//审核状态

//  private file resourceInfo;//资源信息

  public CastBoxEggParameters() {
  }

  public CastBoxEggParameters(String specification, int boxNumber, CastBoxEggItem hideBoxEgg, List<CastBoxEggItem> commonBoxEggList, String walletAddress, String seriesName, String seriesIntroduction) {
    this.specification = specification;
    this.boxNumber = boxNumber;
    this.hideBoxEgg = hideBoxEgg;
    this.commonBoxEggList = commonBoxEggList;
    this.walletAddress = walletAddress;
    this.seriesName = seriesName;
    this.seriesIntroduction = seriesIntroduction;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  public int getBoxNumber() {
    return boxNumber;
  }

  public void setBoxNumber(int boxNumber) {
    this.boxNumber = boxNumber;
  }

  public CastBoxEggItem getHideBoxEgg() {
    return hideBoxEgg;
  }

  public void setHideBoxEgg(CastBoxEggItem hideBoxEgg) {
    this.hideBoxEgg = hideBoxEgg;
  }

  public List<CastBoxEggItem> getCommonBoxEggList() {
    return commonBoxEggList;
  }

  public void setCommonBoxEggList(List<CastBoxEggItem> commonBoxEggList) {
    this.commonBoxEggList = commonBoxEggList;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

  public void setWalletAddress(String walletAddress) {
    this.walletAddress = walletAddress;
  }

  public String getSeriesName() {
    return seriesName;
  }

  public void setSeriesName(String seriesName) {
    this.seriesName = seriesName;
  }

  public String getSeriesIntroduction() {
    return seriesIntroduction;
  }

  public void setSeriesIntroduction(String seriesIntroduction) {
    this.seriesIntroduction = seriesIntroduction;
  }
}
