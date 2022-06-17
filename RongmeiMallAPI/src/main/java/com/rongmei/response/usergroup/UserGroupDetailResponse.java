package com.rongmei.response.usergroup;

import com.rongmei.response.Response;
import java.util.List;

public class UserGroupDetailResponse extends Response {

  private int id;
  private String title;
  private long largeCoins;
  private long largePrice;
  private int userGroupId;
  private String avatarUrl;
  private int minBuyNum;
  private int maxBuyNum;
  private List<String> tags;
  private String description;
  private String firstType;
  private String secondType;

  public UserGroupDetailResponse() {
  }

  public UserGroupDetailResponse(int id, String title, long largeCoins, long largePrice,
      int userGroupId, String avatarUrl, int minBuyNum, int maxBuyNum,
      List<String> tags, String description, String firstType, String secondType) {
    this.id = id;
    this.title = title;
    this.largeCoins = largeCoins;
    this.largePrice = largePrice;
    this.userGroupId = userGroupId;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getLargeCoins() {
    return largeCoins;
  }

  public void setLargeCoins(long largeCoins) {
    this.largeCoins = largeCoins;
  }

  public long getLargePrice() {
    return largePrice;
  }

  public void setLargePrice(long largePrice) {
    this.largePrice = largePrice;
  }

  public int getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(int userGroupId) {
    this.userGroupId = userGroupId;
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
