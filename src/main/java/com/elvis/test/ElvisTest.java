package com.elvis.test;


import tool.DSUtil;

import java.time.LocalDateTime;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {
    public static void main(String[] args) throws Exception {
        LocalDateTime time = LocalDateTime.now();

        System.out.println(time.toLocalDate());
        System.out.println(time.toLocalTime());
        System.out.println(DSUtil.dayStart(time));
        System.out.println(DSUtil.dayEnd(time));
        System.out.println(DSUtil.monthStart(time));
        System.out.println(DSUtil.monthEnd(time));
        System.out.println(DSUtil.yearStart(time));
        System.out.println(DSUtil.yearEnd(time));
        System.out.println(DSUtil.formatTime(DSUtil.dayStart(time), DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(DSUtil.formatTime(DSUtil.dayEnd(time), DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(DSUtil.formatTime(DSUtil.monthStart(time), DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(DSUtil.formatTime(DSUtil.monthEnd(time), DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(DSUtil.formatTime(DSUtil.yearStart(time), DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(DSUtil.formatTime(DSUtil.yearEnd(time), DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));

        System.out.println(DSUtil.parseTime("2023-02-01 23:55:55.555", DSUtil.Pattern.yyyy_MM_dd_HH_mm_ss_SSS));
    }
}