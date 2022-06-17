package com.rongmei.response.metrics;

import com.rongmei.response.Response;

public class MetricsCountResponse extends Response {

  private long count;

  public MetricsCountResponse() {
  }

  public MetricsCountResponse(long count) {
    this.count = count;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
