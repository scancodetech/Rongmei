package com.rongmei.response.notice;

public class NoticeItem {

  private int id;
  private int type;
  private String title;
  private String content;
  private String fromUsername;
  private long createTime;

  public NoticeItem() {
  }

  public NoticeItem(int id, int type, String title, String content, String fromUsername,
      long createTime) {
    this.id = id;
    this.type = type;
    this.title = title;
    this.content = content;
    this.fromUsername = fromUsername;
    this.createTime = createTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getFromUsername() {
    return fromUsername;
  }

  public void setFromUsername(String fromUsername) {
    this.fromUsername = fromUsername;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
