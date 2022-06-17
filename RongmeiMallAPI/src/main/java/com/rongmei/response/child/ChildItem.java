package com.rongmei.response.child;

import com.rongmei.publicdatas.chlid.ChildPic;
import java.util.List;

public class ChildItem {

  private int id;
  private String groupName;
  private List<ChildPic> childPicList;
  private String description;
  private String username;
  private String secretName;
  private int price;
  private long createTime;
  private long updateTime;

  public ChildItem() {
  }

  public ChildItem(int id, String groupName,
      List<ChildPic> childPicList, String description, String username, String secretName,
      int price,
      long createTime, long updateTime) {
    this.id = id;
    this.groupName = groupName;
    this.childPicList = childPicList;
    this.description = description;
    this.username = username;
    this.secretName = secretName;
    this.price = price;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public List<ChildPic> getChildPicList() {
    return childPicList;
  }

  public void setChildPicList(List<ChildPic> childPicList) {
    this.childPicList = childPicList;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSecretName() {
    return secretName;
  }

  public void setSecretName(String secretName) {
    this.secretName = secretName;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
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
