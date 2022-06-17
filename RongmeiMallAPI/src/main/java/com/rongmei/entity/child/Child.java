package com.rongmei.entity.child;

import com.rongmei.publicdatas.chlid.ChildPic;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chlid")
public class Child {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "group_name")
  private String groupName;
  @Column(name = "child_pic_list")
  @ElementCollection(targetClass = ChildPic.class)
  private List<ChildPic> childPicList;
  @Column(name = "description")
  private String description;
  @Column(name = "username")
  private String username;
  @Column(name = "secret_name")
  private String secretName;
  @Column(name = "price")
  private int price;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active", columnDefinition = "tinyint DEFAULT 2")
  private boolean isActive;

  public Child() {
  }

  public Child(String groupName, List<ChildPic> childPicList, String description, String username,
      String secretName, int price, long createTime, long updateTime, boolean isActive) {
    this.groupName = groupName;
    this.childPicList = childPicList;
    this.description = description;
    this.username = username;
    this.secretName = secretName;
    this.price = price;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
