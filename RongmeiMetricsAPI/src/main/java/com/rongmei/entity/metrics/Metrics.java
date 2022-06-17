package com.rongmei.entity.metrics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "metrics")
public class Metrics {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "username")
  private String username;
  @Column(name = "metrics_key")
  private String metricsKey;
  @Column(name = "metrics_msg")
  private String metricsMsg;
  @Column(name = "curr_time")
  private long currTime;

  public Metrics() {
  }

  public Metrics(String username, String metricsKey, String msg, long currTime) {
    this.username = username;
    this.metricsKey = metricsKey;
    this.metricsMsg = msg;
    this.currTime = currTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMetricsKey() {
    return metricsKey;
  }

  public void setMetricsKey(String metricsKey) {
    this.metricsKey = metricsKey;
  }

  public String getMetricsMsg() {
    return metricsMsg;
  }

  public void setMetricsMsg(String metricsMsg) {
    this.metricsMsg = metricsMsg;
  }

  public long getCurrTime() {
    return currTime;
  }

  public void setCurrTime(long currTime) {
    this.currTime = currTime;
  }
}
