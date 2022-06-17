package com.rongmei.response.lottery;

public class LotteryItem {

  private long id;
  private String username;
  private String name;
  private String phone;
  private String email;
  private String prizeType;
  private String prizeName;
  private long createTime;
  private long updateTime;

  public LotteryItem() {
  }

  public LotteryItem(long id, String username, String name, String phone, String email,
      String prizeType, String prizeName, long createTime, long updateTime) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.prizeType = prizeType;
    this.prizeName = prizeName;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPrizeType() {
    return prizeType;
  }

  public void setPrizeType(String prizeType) {
    this.prizeType = prizeType;
  }

  public String getPrizeName() {
    return prizeName;
  }

  public void setPrizeName(String prizeName) {
    this.prizeName = prizeName;
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
