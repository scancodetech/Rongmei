package com.rongmei.response.usergroup;

import com.rongmei.response.user.UserItem;
import java.util.List;

public class UserGroup {

  private int id;
  private String title;
  private List<UserItem> userItems;
  private long largeCoins;
  private long createTime;
  private long updateTime;

  public UserGroup() {
  }

  public UserGroup(int id, String title, List<UserItem> userItems, long largeCoins, long createTime,
      long updateTime) {
    this.id = id;
    this.title = title;
    this.userItems = userItems;
    this.largeCoins = largeCoins;
    this.createTime = createTime;
    this.updateTime = updateTime;
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

  public List<UserItem> getUserItems() {
    return userItems;
  }

  public void setUserItems(List<UserItem> userItems) {
    this.userItems = userItems;
  }

  public long getLargeCoins() {
    return largeCoins;
  }

  public void setLargeCoins(long largeCoins) {
    this.largeCoins = largeCoins;
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
}
