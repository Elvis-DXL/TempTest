package com.elvis.test;


import com.elvis.commons.utils.NumberUtil;
import com.elvis.commons.utils.StrUtil;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {

    public static void main(String[] args) {
        int index = 1;
        while (true) {
            double result = NumberUtil.doubleFmt((((double) ((int) Math.round(Math.random() * 4 + 2))) * 0.1D + (Math.random() * 0.1D)) * 0.001D, 8) + 104.102997D;
            System.out.println(NumberUtil.doubleFmt(result, 8));
            index++;
            if (index > 100) {
                break;
            }
        }
    }

    private static void nextFx(Integer fx) {
        Integer nextFx = null;
        while (null == nextFx) {
            int round = (int) Math.round(Math.random() * 3 + 1);
            if (null == fx) {
                nextFx = round;
                break;
            }
            if (fx == 1 && round == 3) {
                continue;
            }
            if (fx == 3 && round == 1) {
                continue;
            }
            if (fx == 2 && round == 4) {
                continue;
            }
            if (fx == 4 && round == 2) {
                continue;
            }
            nextFx = round;
        }
        System.out.println(fx + "=====================" + nextFx);
    }
}