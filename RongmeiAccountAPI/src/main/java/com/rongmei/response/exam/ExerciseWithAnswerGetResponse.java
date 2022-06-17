package com.rongmei.response.exam;

import com.rongmei.response.Response;
import java.util.List;

public class ExerciseWithAnswerGetResponse extends Response {

  private List<ExerciseWithAnswerItem> exerciseItemList;

  public ExerciseWithAnswerGetResponse() {
  }

  public ExerciseWithAnswerGetResponse(List<ExerciseWithAnswerItem> exerciseItemList) {
    this.exerciseItemList = exerciseItemList;
  }

  public List<ExerciseWithAnswerItem> getExerciseItemList() {
    return exerciseItemList;
  }

  public void setExerciseItemList(List<ExerciseWithAnswerItem> exerciseItemList) {
    this.exerciseItemList = exerciseItemList;
  }
}
