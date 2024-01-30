package com.elvis.test;

import newproject.enums.DatePattern;
import newproject.utils.StaticUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/12/8 16:14
 */
public class UtilTest {
    public static void main(String[] args) throws Exception {
        getMinutesOfDay(new Date());
        System.out.println(StaticUtil.dateMinutesOfDay(new Date()));
    }

    public static long getMinutesOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date endTime = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        long diff = endTime.getTime() - startTime.getTime();
        long diffMinutes = diff / 1000L / 60L;
        System.out.println(diff);
        System.out.println(diffMinutes);
//        System.out.println(StaticUtil.formatDate(startTime, DatePattern.yyyy_MM_dd_HH_mm_ss_SSS));
//        System.out.println(StaticUtil.formatDate(endTime, DatePattern.yyyy_MM_dd_HH_mm_ss_SSS));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        int hour = calendar2.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar2.get(Calendar.MINUTE);
        int minutesOfDay = hour * 60 + minutes;

        System.out.println(StaticUtil.formatDate(calendar.getTime(), DatePattern.yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(hour + "------" + minutes);
        System.out.println(minutesOfDay);
        return 0L;
    }
}
