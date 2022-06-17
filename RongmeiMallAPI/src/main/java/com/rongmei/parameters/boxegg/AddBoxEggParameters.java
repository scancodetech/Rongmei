package com.rongmei.parameters.boxegg;

import java.util.List;

public class AddBoxEggParameters {

//  private String specification;//套盒规格  1: 3*3 ， 2: 3*4；

//  private int number;//套盒数量

  private String  coverUrl;//封面图路径

  private String  seriesName;//系列名称

  private long  startTime;//售卖开始时间

  private long  endTime;//售卖开始时间

  private String seriesIntroduction;//系列简介 其他说明

  private int price;//定价
//  private BoxEgg hideModel;//隐藏款
//
//  private List<BoxEgg> commonModel;//普通款



  public AddBoxEggParameters() {
  }

  public AddBoxEggParameters(String coverUrl, String seriesName, long startTime, long endTime, String seriesIntroduction, int price) {
    this.coverUrl = coverUrl;
    this.seriesName = seriesName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.seriesIntroduction = seriesIntroduction;
    this.price = price;
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

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
