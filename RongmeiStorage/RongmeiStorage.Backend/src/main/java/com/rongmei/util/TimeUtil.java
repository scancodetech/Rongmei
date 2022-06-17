package com.rongmei.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeUtil {

  public static List<Long> getCurrentTwelveMonthStartTime() {
    LocalDateTime today = LocalDateTime.now();
    List<Long> times = new ArrayList<>();
    for (int i = 12; i > 0; i--) {
      LocalDateTime monthStart = today.minusMonths(i);
      times.add(Timestamp.valueOf(monthStart).getTime());
    }
    return times;
  }

  public static List<Long> getCurrentSomeMinuteStartTime() {
    LocalDateTime now = LocalDateTime.now();
    List<Long> times = new ArrayList<>();
    for (int i = 12; i > 0; i--) {
      LocalDateTime minuteStart = now.minusMinutes(i);
      times.add(Timestamp.valueOf(minuteStart).getTime());
    }
    return times;
  }
}
