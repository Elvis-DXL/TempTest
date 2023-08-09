package com.elvis.test.bean;

import com.elvis.commons.utils.BeanUtil;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:45
 */
public class BeanTest {
    public static void main(String args[]) {
        Src src = new Src(12, "目标", "12312");
        Aim aim = new Aim();
        BeanUtil.copyFields(src, aim);
        System.out.println(aim);

        System.out.println(BeanUtil.newByObj(src, Aim.class));
    }
}
