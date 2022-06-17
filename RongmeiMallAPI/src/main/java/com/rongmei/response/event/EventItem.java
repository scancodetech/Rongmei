package com.rongmei.response.event;

public class EventItem {

  private int id;
  private String title;
  private long startTime;
  private long endTime;
  private String coverUrl;
  private long createTime;
  private long updateTime;
  private boolean isActive;
  private int status;//0:审核中 1:未通过 2:已通过
  private String msg;

  public EventItem() {
  }

  public EventItem(int id, String title, long startTime, long endTime,
      String coverUrl, long createTime, long updateTime, boolean isActive, int status,
      String msg) {
    this.id = id;
    this.title = title;
    this.startTime = startTime;
    this.endTime = endTime;
    this.coverUrl = coverUrl;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
    this.status = status;
    this.msg = msg;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
