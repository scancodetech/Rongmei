package com.rongmei.parameters.groupshopping;

import java.util.List;

public class GroupShoppingUpdateParameters {

  private int id;
  private String title;
  private long price;
  private String coverUrl;
  private List<String> tags;
  private String contentUrl;
  private String information;
  private String description;
  private String comment;
  private long startTime;
  private long endTime;

  public GroupShoppingUpdateParameters() {
  }

  public GroupShoppingUpdateParameters(int id, String title, long price, String coverUrl,
      List<String> tags, String contentUrl, String information, String description,
      String comment, long startTime, long endTime) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.coverUrl = coverUrl;
    this.tags = tags;
    this.contentUrl = contentUrl;
    this.information = information;
    this.description = description;
    this.comment = comment;
    this.startTime = startTime;
    this.endTime = endTime;
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

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(String contentUrl) {
    this.contentUrl = contentUrl;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }
}
