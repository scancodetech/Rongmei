package com.rongmei.parameters.comment;

public class CommentAddParameters {

  private String text;
  private int randomNum;

  public CommentAddParameters() {
  }

  public CommentAddParameters(String text, int randomNum) {
    this.text = text;
    this.randomNum = randomNum;
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
}
