package com.rongmei.entity.usergroup;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group_info")
public class UserGroupInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "user_group_id")
  private int userGroupId;
  @Column(name = "large_price")
  private long largePrice;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Column(name = "min_buy_num")
  private int minBuyNum;
  @Column(name = "max_buy_num")
  private int maxBuyNum;
  @Column(name = "tags")
  @ElementCollection(targetClass = String.class)
  private List<String> tags;
  @Column(name = "description", columnDefinition = "mediumtext")
  private String description;
  @Column(name = "first_type")
  private String firstType;
  @Column(name = "second_type")
  private String secondType;

  public UserGroupInfo() {
  }


  public UserGroupInfo(int userGroupId, long largePrice, String avatarUrl, int minBuyNum,
      int maxBuyNum, List<String> tags, String description, String firstType,
      String secondType) {
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
