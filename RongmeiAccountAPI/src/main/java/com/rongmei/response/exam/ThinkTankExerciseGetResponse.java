package com.rongmei.response.exam;

import com.rongmei.response.Response;
import java.util.List;

public class ThinkTankExerciseGetResponse extends Response {

  private List<ThinkTankExerciseItem> thinkTankExerciseItems;

  public ThinkTankExerciseGetResponse() {
  }

  public ThinkTankExerciseGetResponse(
      List<ThinkTankExerciseItem> thinkTankExerciseItems) {
    this.thinkTankExerciseItems = thinkTankExerciseItems;
  }

  public List<ThinkTankExerciseItem> getThinkTankExerciseItems() {
    return thinkTankExerciseItems;
  }

  public void setThinkTankExerciseItems(
      List<ThinkTankExerciseItem> thinkTankExerciseItems) {
    this.thinkTankExerciseItems = thinkTankExerciseItems;
  }
}
