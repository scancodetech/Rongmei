package com.rongmei.response.stats;

public class TimeStatsItem {

  private long time;
  private int num;

  public TimeStatsItem() {
  }

  public TimeStatsItem(long time, int num) {
    this.time = time;
    this.num = num;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }
}
