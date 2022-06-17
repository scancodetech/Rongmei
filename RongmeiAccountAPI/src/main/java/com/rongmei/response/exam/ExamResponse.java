package com.rongmei.response.exam;

import com.rongmei.response.Response;

public class ExamResponse extends Response {

  private int totalCount;
  private int okCount;
  private int score;

  public ExamResponse() {
  }

  public ExamResponse(int totalCount, int okCount, int score) {
    this.totalCount = totalCount;
    this.okCount = okCount;
    this.score = score;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getOkCount() {
    return okCount;
  }

  public void setOkCount(int okCount) {
    this.okCount = okCount;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
