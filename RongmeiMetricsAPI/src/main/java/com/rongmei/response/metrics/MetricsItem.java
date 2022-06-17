package com.rongmei.response.metrics;

public class MetricsItem {

  private String metricsKey;
  private int cnt;

  public MetricsItem() {
  }

  public MetricsItem(String metricsKey, long cnt) {
    this.metricsKey = metricsKey;
    this.cnt = (int) cnt;
  }

  public String getMetricsKey() {
    return metricsKey;
  }

  public void setMetricsKey(String metricsKey) {
    this.metricsKey = metricsKey;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
}
