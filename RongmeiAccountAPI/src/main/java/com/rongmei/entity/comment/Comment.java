package com.rongmei.entity.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "username")
  private String username;
  @Column(name = "text")
  private String text;
  @Column(name = "random_num")
  private int randomNum;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "is_active")
  private boolean isActive;

  public Comment() {
  }

  public Comment(String username, String text, int randomNum, long createTime,
      boolean isActive) {
    this.username = username;
    this.text = text;
    this.randomNum = randomNum;
    this.createTime = createTime;
    this.isActive = isActive;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
