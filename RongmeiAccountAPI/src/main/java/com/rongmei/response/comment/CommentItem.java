package com.rongmei.response.comment;

public class CommentItem {

  private int id;
  private String username;
  private String text;
  private int randomNum;
  private long createTime;

  public CommentItem() {
  }

  public CommentItem(int id, String username, String text, int randomNum, long createTime) {
    this.id = id;
    this.username = username;
    this.text = text;
    this.randomNum = randomNum;
    this.createTime = createTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getRandomNum() {
    return randomNum;
  }

  public void setRandomNum(int randomNum) {
    this.randomNum = randomNum;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
