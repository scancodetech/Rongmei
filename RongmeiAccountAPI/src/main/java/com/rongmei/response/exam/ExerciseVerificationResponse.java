package com.rongmei.response.exam;

import com.rongmei.response.Response;

public class ExerciseVerificationResponse extends Response {

  private long id;
  private boolean isCorrect;

  public ExerciseVerificationResponse() {
  }

  public ExerciseVerificationResponse(long id, boolean isCorrect) {
    this.id = id;
    this.isCorrect = isCorrect;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public boolean isCorrect() {
    return isCorrect;
  }

  public void setCorrect(boolean correct) {
    isCorrect = correct;
  }
}
