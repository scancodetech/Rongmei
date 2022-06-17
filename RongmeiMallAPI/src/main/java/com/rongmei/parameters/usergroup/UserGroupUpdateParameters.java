package com.rongmei.parameters.usergroup;

import java.util.List;

public class UserGroupUpdateParameters {

  private int id;
  private int userGroupId;
  private long largePrice;
  private String avatarUrl;
  private int minBuyNum;
  private int maxBuyNum;
  private List<String> tags;
  private String description;
  private String firstType;
  private String secondType;

  public UserGroupUpdateParameters() {
  }

  public UserGroupUpdateParameters(int id, int userGroupId, long largePrice,
      String avatarUrl, int minBuyNum, int maxBuyNum, List<String> tags, String description,
      String firstType, String secondType) {
    this.id = id;
    this.userGroupId = userGroupId;
    this.largePrice = largePrice;
    this.avatarUrl = avatarUrl;
    this.minBuyNum = minBuyNum;
    this.maxBuyNum = maxBuyNum;
    this.tags = tags;
    this.description = description;
    this.firstType = firstType;
    this.secondType = secondType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(int userGroupId) {
    this.userGroupId = userGroupId;
  }

  public long getLargePrice() {
    return largePrice;
  }

  public void setLargePrice(long largePrice) {
    this.largePrice = largePrice;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public int getMinBuyNum() {
    return minBuyNum;
  }

  public void setMinBuyNum(int minBuyNum) {
    this.minBuyNum = minBuyNum;
  }

  public int getMaxBuyNum() {
    return maxBuyNum;
  }

  public void setMaxBuyNum(int maxBuyNum) {
    this.maxBuyNum = maxBuyNum;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFirstType() {
    return firstType;
  }

  public void setFirstType(String firstType) {
    this.firstType = firstType;
  }

  public String getSecondType() {
    return secondType;
  }

  public void setSecondType(String secondType) {
    this.secondType = secondType;
  }
}
