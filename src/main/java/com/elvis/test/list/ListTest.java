package com.elvis.test.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/8/24 13:58
 */
public class ListTest {

    public static void main(String[] args) {
        List<String> strList = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        int count = 1;

        Map<String, Integer> statMap = new HashMap<>();
        while (true) {
            String aim = randomOne(strList);
            System.out.println(aim);
            Integer stat = statMap.get(aim);
            stat = null == stat ? 1 : (stat + 1);
            statMap.put(aim, stat);
            if (count >= 100) {
                break;
            }
            count++;
        }
        System.out.println(statMap);
    }

    private static <T> T randomOne(List<T> srcList) {
        return srcList.get((int) (Math.random() * srcList.size()));
    }
}
