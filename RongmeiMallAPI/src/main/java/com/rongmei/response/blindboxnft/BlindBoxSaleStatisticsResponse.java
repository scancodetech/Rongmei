package com.rongmei.response.blindboxnft;

import com.rongmei.response.Response;

public class BlindBoxSaleStatisticsResponse extends Response {

  private int count;

  public BlindBoxSaleStatisticsResponse() {
  }

  public BlindBoxSaleStatisticsResponse(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
