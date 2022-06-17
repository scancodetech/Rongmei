package com.rongmei.entity.exam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exercise")
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "tag")
  private String tag;
  @Column(name = "question")
  private String question;
  @Column(name = "selections")
  private String selections;
  @Column(name = "answer")
  private String answer;
  @Column(name = "is_active")
  private boolean isActive;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public Exercise() {
  }

  public Exercise(String tag, String question, String selections, String answer,
      boolean isActive, long createTime, long updateTime) {
    this.tag = tag;
    this.question = question;
    this.selections = selections;
    this.answer = answer;
    this.isActive = isActive;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getSelections() {
    return selections;
  }

  public void setSelections(String selections) {
    this.selections = selections;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
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
