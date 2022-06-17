package com.rongmei.parameters.notice;

public class NoticeSendParameters {

  private int id;
  private String title;
  private String content;
  private String fromUsername;
  private String toUsername;
  private int type;//0:小助手 1:交易通知 2:活动通知

  public NoticeSendParameters() {
  }

  public NoticeSendParameters(int id, String title, String content, String fromUsername,
      String toUsername, int type) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.fromUsername = fromUsername;
    this.toUsername = toUsername;
    this.type = type;
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

  public String getToUsername() {
    return toUsername;
  }

  public void setToUsername(String toUsername) {
    this.toUsername = toUsername;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
