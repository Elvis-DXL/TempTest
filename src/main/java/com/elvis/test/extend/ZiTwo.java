package com.elvis.test.extend;

/**
 * @author : Elvis
 * @since : 2023/2/2 09:54
 */
public class ZiTwo implements FuInterface {

    @Override
    public void init() {
        System.out.println("Zi Two Init");
    }

    @Override
    public void testMethod() {
        System.out.println("Zi Two Running");
    }

    @Override
    public void testJs(int num) {
        System.out.println(num + 1000);
    }

}
