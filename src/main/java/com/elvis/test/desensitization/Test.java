package com.elvis.test.desensitization;

import com.elvis.commons.utils.AnnoUtil;

/**
 * @author : Elvis
 * @since : 2023/2/27 10:21
 */
public class Test {
    public static void main(String[] args) {
        Aim aim = new Aim("1231","1312312312312321");
        AnnoUtil.desensitization(aim);
        System.out.println(aim.getCode());
    }
}
