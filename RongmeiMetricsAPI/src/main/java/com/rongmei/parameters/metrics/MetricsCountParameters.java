package com.rongmei.parameters.metrics;

import java.util.List;

public class MetricsCountParameters {

  private List<String> keys;
  private long startTime;
  private long endTime;

  public MetricsCountParameters() {
  }

  public MetricsCountParameters(List<String> keys, long startTime, long endTime) {
    this.keys = keys;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public List<String> getKeys() {
    return keys;
  }

  public void setKeys(List<String> keys) {
    this.keys = keys;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }
}
