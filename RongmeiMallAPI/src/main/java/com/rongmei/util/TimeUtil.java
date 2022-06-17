package com.rongmei.util;


import java.text.SimpleDateFormat;
import java.util.*;

public class TimeUtil {

    public static final Long SEVEN = 604800000L;
    public static final Long MONTH = 2592000000L;
    public static final Long DAY = 86400000L;


    public static long getTodayStartTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getTodayEndTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    public static long getNowTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        return calendar.getTimeInMillis();
    }


        // 七天钱开始 现在

    public static long getSevenTime(Long time) {
        //设置时区
        time -= SEVEN;
       return time;
    }


    public static long getMonthTime(Long time) {
        //设置时区
        time -= MONTH;
        return time;
    }

    public static long getLastTime(Long time) {
        //设置时区
        time -= DAY;
        return time;
    }


    /**
     * 获取当月开始
     * @param time
     */
    public static Long getMonthBegin(Long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),Locale.CHINA);
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DATE,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月结束
     * @param time
     */
    public static Long getMonthEnd(Long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),Locale.CHINA);
        calendar.setTimeInMillis(time);
        int month = calendar.get(Calendar.MONTH) + 1;
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            calendar.set(Calendar.DATE,31);
        }else {
            calendar.set(Calendar.DATE,30);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();

    }

    public static List<Long> getMonthBeginAndEnd(long time){
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(0,getMonthBegin(time));
        longs.add(1,getMonthEnd(time));
        return longs;
    }

    public static void main(String[] args) {
        Date begin = new Date(getMonthBegin(System.currentTimeMillis()));
        Date end = new Date(getMonthEnd(System.currentTimeMillis()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(begin).toString());
        System.out.println(simpleDateFormat.format(end).toString());
    }
}
