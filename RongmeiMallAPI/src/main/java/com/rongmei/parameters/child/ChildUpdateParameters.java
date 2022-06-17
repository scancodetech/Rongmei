package com.rongmei.parameters.child;

import com.rongmei.publicdatas.chlid.ChildPic;
import java.util.List;

public class ChildUpdateParameters {

  private int id;
  private String groupName;
  private List<ChildPic> childPicList;
  private String description;
  private String secretName;
  private int price;

  public ChildUpdateParameters() {
  }

  public ChildUpdateParameters(int id, String groupName,
      List<ChildPic> childPicList, String description, String secretName, int price) {
    this.id = id;
    this.groupName = groupName;
    this.childPicList = childPicList;
    this.description = description;
    this.secretName = secretName;
    this.price = price;
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
}
