package com.rongmei.parameters.metrics;

public class MetricsAddParameters {

  private String key;
  private String msg;

  public MetricsAddParameters() {
  }

  public MetricsAddParameters(String key, String msg) {
    this.key = key;
    this.msg = msg;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}