package com.elvis.test.extend;

/**
 * @author : Elvis
 * @since : 2023/2/2 09:54
 */
public class ExtendTest {

    public static void main(String args[]) {
        FuInterface fu1 = new ZiOne();
        fu1.testMethod();
        fu1.testJs(100);
        fu1.init();

        System.out.println("***********************");

        FuInterface fu2 = new ZiTwo();
        fu2.testMethod();
        fu2.testJs(1131313);
        fu2.init();

        System.out.println("***********************");

        SuperInterface su1 = new ZiOne();
        su1.init();

        SuperInterface su2 = new ZiTwo();
        su2.init();
    }
}
