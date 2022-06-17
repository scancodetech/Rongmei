package com.rongmei.parameters.exam;

import com.rongmei.publicdatas.exam.UserExerciseItem;
import java.util.List;

public class ExamSubmitParameters {

  private List<UserExerciseItem> userExercises;

  public ExamSubmitParameters() {
  }

  public ExamSubmitParameters(List<UserExerciseItem> userExercises) {
    this.userExercises = userExercises;
  }

  public List<UserExerciseItem> getUserExercises() {
    return userExercises;
  }

  public void setUserExercises(List<UserExerciseItem> userExercises) {
    this.userExercises = userExercises;
  }
}
