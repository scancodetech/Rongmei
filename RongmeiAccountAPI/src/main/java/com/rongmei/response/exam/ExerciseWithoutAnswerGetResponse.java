package com.rongmei.response.exam;

import com.rongmei.response.Response;
import java.util.List;

public class ExerciseWithoutAnswerGetResponse extends Response {

  private List<ExerciseWithoutAnswerItem> exerciseWithoutAnswerItemList;

  public ExerciseWithoutAnswerGetResponse() {
  }

  public ExerciseWithoutAnswerGetResponse(
      List<ExerciseWithoutAnswerItem> exerciseWithoutAnswerItemList) {
    this.exerciseWithoutAnswerItemList = exerciseWithoutAnswerItemList;
  }

  public List<ExerciseWithoutAnswerItem> getExerciseWithoutAnswerItemList() {
    return exerciseWithoutAnswerItemList;
  }

  public void setExerciseWithoutAnswerItemList(
      List<ExerciseWithoutAnswerItem> exerciseWithoutAnswerItemList) {
    this.exerciseWithoutAnswerItemList = exerciseWithoutAnswerItemList;
  }
}
