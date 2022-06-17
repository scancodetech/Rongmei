package com.rongmei.response.boxorder;

import java.util.List;

public class BoxOrderShareItem {

  private long id;
  private String imageUrl;
  private List<String> usernames;
  private int price;
  private String cooperateType;
  private long endTime;

  public BoxOrderShareItem() {
  }

  public BoxOrderShareItem(long id, String imageUrl, List<String> usernames, int price,

      String cooperateType, long endTime) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.usernames = usernames;
    this.price = price;
    this.cooperateType = cooperateType;
    this.endTime = endTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<String> getUsernames() {
    return usernames;
  }

  public void setUsernames(List<String> usernames) {
    this.usernames = usernames;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }


  public String getCooperateType() {
    return cooperateType;
  }

  public void setCooperateType(String cooperateType) {
    this.cooperateType = cooperateType;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }
}
