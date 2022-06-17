package com.rongmei.parameters.boxegg;

public class CastBoxEggItem {

  private String  coverUrl;//封面图路径

  private String  boxEggName;//盒蛋名称

  private String boxEggIntroduction;//盒蛋简介

  private String resourceInfoURL;//源文件

  private int limitNumber;//限量数







  public CastBoxEggItem() {
  }

  public CastBoxEggItem(String coverUrl, String boxEggName, String boxEggIntroduction, String resourceInfoURL, int limitNumber) {
    this.coverUrl = coverUrl;
    this.boxEggName = boxEggName;
    this.boxEggIntroduction = boxEggIntroduction;
    this.resourceInfoURL = resourceInfoURL;
    this.limitNumber = limitNumber;
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

  public String getResourceInfoURL() {
    return resourceInfoURL;
  }

  public void setResourceInfoURL(String resourceInfoURL) {
    this.resourceInfoURL = resourceInfoURL;
  }

  public int getLimitNumber() {
    return limitNumber;
  }

  public void setLimitNumber(int limitNumber) {
    this.limitNumber = limitNumber;
  }

}
