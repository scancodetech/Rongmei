package com.rongmei.parameters.event;

public class EventUploadParameters {

  private int id;
  private String title;
  private long startTime;
  private long endTime;
  private String coverUrl;
  private String description;

  public EventUploadParameters() {
  }

  public EventUploadParameters(int id, String title, long startTime, long endTime,
      String coverUrl, String description) {
    this.id = id;
    this.title = title;
    this.startTime = startTime;
    this.endTime = endTime;
    this.coverUrl = coverUrl;
    this.description = description;
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

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
