package com.rongmei.response.relation;

import com.rongmei.response.Response;

public class LikeStatisticsResponse extends Response {

  private int count;

  public LikeStatisticsResponse() {
  }

  public LikeStatisticsResponse(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
