package com.elvis.test;


import com.elvis.commons.utils.StrUtil;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {
    public static void main(String[] args) {
        int index = 1;
        while (index <= 100) {
            System.out.println(StrUtil.randomNumberStrByLength(6));
            index++;
        }
    }
}