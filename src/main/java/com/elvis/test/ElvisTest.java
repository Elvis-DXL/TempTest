package com.elvis.test;


import project.base.DSUtil;

import java.time.LocalDateTime;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(DSUtil.formatTime(DSUtil.dayStart(localDateTime), DSUtil.DatePattern.c_yyyy_MM_dd_HH_mm_ss_SSS));
        System.out.println(DSUtil.formatTime(DSUtil.dayEnd(localDateTime.plusDays(1)), DSUtil.DatePattern.c_yyyy_MM_dd_HH_mm_ss_SSS));
    }
}