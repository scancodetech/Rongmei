package com.rongmei.response.usergroup;


public class UserGroupInfoItem {

  private int userGroupId;
  private String title;
  private String avatarUrl;
  private long largePrice;

  public UserGroupInfoItem() {
  }

  public UserGroupInfoItem(int userGroupId, String title, String avatarUrl, long largePrice) {
    this.userGroupId = userGroupId;
    this.title = title;
    this.avatarUrl = avatarUrl;
    this.largePrice = largePrice;
  }

  public int getUserGroupId() {
    return userGroupId;
  }

  public void setUserGroupId(int userGroupId) {
    this.userGroupId = userGroupId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public long getLargePrice() {
    return largePrice;
  }

  public void setLargePrice(long largePrice) {
    this.largePrice = largePrice;
  }
}
