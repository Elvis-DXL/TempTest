package com.elvis.test.extend;

/**
 * @author : Elvis
 * @since : 2023/2/2 09:53
 */
public class ZiOne implements FuInterface {

    @Override
    public void init() {
        System.out.println("Zi One Init");
    }

    @Override
    public void testMethod() {
        System.out.println("Zi One Running");
    }

    @Override
    public void testJs(int num) {
        System.out.println(num + 1);
    }

}
