package com.rongmei.parameters.exam;

public class ExerciseUpdateParameters {

  private long id;
  private String tag;
  private String question;
  private String answer;
  private String selections;

  public ExerciseUpdateParameters() {
  }

  public ExerciseUpdateParameters(long id, String tag, String question, String answer,
      String selections) {
    this.id = id;
    this.tag = tag;
    this.question = question;
    this.answer = answer;
    this.selections = selections;
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

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getSelections() {
    return selections;
  }

  public void setSelections(String selections) {
    this.selections = selections;
  }
}
