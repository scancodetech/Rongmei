package com.rongmei.response.commodity;

import com.rongmei.response.Response;

public class CommoditySaleStatisticsResponse extends Response {

  private int count;

  public CommoditySaleStatisticsResponse() {
  }

  public CommoditySaleStatisticsResponse(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
