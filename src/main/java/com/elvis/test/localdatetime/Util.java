package com.elvis.test.localdatetime;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/2/5 9:16
 */
public class Util {

    public static LocalDateTime dayStart(LocalDateTime time) {
        time = null == time ? LocalDateTime.now() : time;
        return time.withHour(0).withMinute(0).withSecond(0);
    }

}
