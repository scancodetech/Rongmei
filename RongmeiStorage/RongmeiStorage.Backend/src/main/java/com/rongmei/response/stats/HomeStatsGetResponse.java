package com.rongmei.response.stats;

import com.rongmei.response.Response;
import java.util.List;

public class HomeStatsGetResponse extends Response {

  private List<TimeStatsItem> monthStatsCount;
  private List<TimeStatsItem> minuteStatsCount;

  public HomeStatsGetResponse() {
  }

  public HomeStatsGetResponse(
      List<TimeStatsItem> monthStatsCount,
      List<TimeStatsItem> minuteStatsCount) {
    this.monthStatsCount = monthStatsCount;
    this.minuteStatsCount = minuteStatsCount;
  }

  public List<TimeStatsItem> getMonthStatsCount() {
    return monthStatsCount;
  }

  public void setMonthStatsCount(List<TimeStatsItem> monthStatsCount) {
    this.monthStatsCount = monthStatsCount;
  }

  public List<TimeStatsItem> getMinuteStatsCount() {
    return minuteStatsCount;
  }

  public void setMinuteStatsCount(
      List<TimeStatsItem> minuteStatsCount) {
    this.minuteStatsCount = minuteStatsCount;
  }
}
