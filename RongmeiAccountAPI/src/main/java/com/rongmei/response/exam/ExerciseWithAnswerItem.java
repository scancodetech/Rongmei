package com.rongmei.response.exam;

public class ExerciseWithAnswerItem {

  private long id;
  private String tag;
  private String question;
  private String selections;
  private String answer;
  private long createTime;
  private long updateTime;

  public ExerciseWithAnswerItem() {
  }

  public ExerciseWithAnswerItem(long id, String tag, String question,
      String selections, String answer, long createTime, long updateTime) {
    this.id = id;
    this.tag = tag;
    this.question = question;
    this.selections = selections;
    this.answer = answer;
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
