package com.rongmei.entity.notice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notice")
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "title")
  private String title;
  @Column(name = "content", columnDefinition = "MEDIUMTEXT")
  private String content;
  @Column(name = "from_username")
  private String fromUsername;//admin:13700000002
  @Column(name = "to_username")
  private String toUsername;
  @Column(name = "notice_type", columnDefinition = "int default 0")
  private int type;//1
  @Column(name = "create_time")
  private long createTime;

  public Notice() {
  }

  public Notice(String title, String content, String fromUsername, String toUsername, int type,
      long createTime) {
    this.title = title;
    this.content = content;
    this.fromUsername = fromUsername;
    this.toUsername = toUsername;
    this.type = type;
    this.createTime = createTime;
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

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
