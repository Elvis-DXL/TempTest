package com.elvis.test.localdatetime;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/2/5 9:16
 */
public class Test {

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        time = time.withNano(999999999);
        System.out.println(time.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println(time.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println(time.with(TemporalAdjusters.firstDayOfYear()));
        System.out.println(time.with(TemporalAdjusters.lastDayOfYear()));
        System.out.println(time.getNano());
    }
}
