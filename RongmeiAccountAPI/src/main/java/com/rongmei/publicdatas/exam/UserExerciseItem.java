package com.rongmei.publicdatas.exam;

import javax.persistence.Embeddable;

@Embeddable
public class UserExerciseItem {

  private long id;
  private String question;
  private String selections;
  private String userAnswer;

  public UserExerciseItem() {
  }

  public UserExerciseItem(long id, String question, String selections, String userAnswer) {
    this.id = id;
    this.question = question;
    this.selections = selections;
    this.userAnswer = userAnswer;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getUserAnswer() {
    return userAnswer;
  }

  public void setUserAnswer(String userAnswer) {
    this.userAnswer = userAnswer;
  }
}
