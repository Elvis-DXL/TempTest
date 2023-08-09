package com.elvis.test;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {

    public static void main(String[] args) {
        List<String> arr = Arrays.asList("代企帆", "何春清", "代小龙", "杨超", "林顺华", "杨皓");
        int index = 1;
        Map<String, Integer> result = new HashMap<>();
        while (true) {
            String res = arr.get((int) Math.floor(Math.random() * arr.size()));
            System.out.println(res);
            Integer count = result.get(res);
            result.put(res, null == count ? 1 : count + 1);
            index++;
            if (index >= 1000) {
                break;
            }
        }
        System.out.println(result);

        System.out.println(arr.get((int) Math.floor(Math.random() * arr.size())));
    }
}