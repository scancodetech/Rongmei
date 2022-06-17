package com.rongmei.entity.account;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group")
public class UserGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "title")
  private String title;
  @Column(name = "usernames")
  @ElementCollection(targetClass = String.class)
  private List<String> usernames;
  @Column(name = "large_coins")
  private long largeCoins;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public UserGroup() {
  }

  public UserGroup(String title, List<String> usernames, long largeCoins, long createTime,
      long updateTime) {
    this.title = title;
    this.usernames = usernames;
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

  public List<String> getUsernames() {
    return usernames;
  }

  public void setUsernames(List<String> usernames) {
    this.usernames = usernames;
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
