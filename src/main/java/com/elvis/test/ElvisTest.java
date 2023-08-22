package com.elvis.test;


import com.elvis.commons.utils.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {

    public static void main(String[] args) {
        List<LatLng> list = new ArrayList<>();
        LatLng latLng = new LatLng();
        latLng.lat = "30.642527";
        latLng.lng = "104.101978";
        latLng.aimStr = latLng.lat + "_" + latLng.lng;
        list.add(latLng);
        int index = 1;
        while (true) {
            LatLng aim = list.get((int) (Math.random() * list.size()));

            LatLng newLatLng = new LatLng();
            newLatLng.lat = lngLatChange(aim.lat);
            newLatLng.lng = lngLatChange(aim.lng);
            newLatLng.aimStr = newLatLng.lat + "_" + newLatLng.lng;
            list.add(newLatLng);
            index++;
            if (index >= 1000) {
                break;
            }
        }
        list.forEach(it -> {
            System.out.println(it.lat + "----------" + it.lng);
        });

        System.out.println("*****************************************");

        Map<String, List<LatLng>> listMap = list.stream().collect(Collectors.groupingBy(it -> it.aimStr));
        for (String key : listMap.keySet()) {
            List<LatLng> objList = listMap.get(key);
            if (objList.size() < 2) {
                continue;
            }
            System.out.println(key);
        }
    }

    private static String lngLatChange(String lngLat) {
        double ll = Double.parseDouble(lngLat);
        double romLL = NumberUtil
                .doubleFmt((((double) ((int) Math.round(Math.random() * 4 + 2))) * 0.1D + (Math.random() * 0.1D)) * 0.002, 8);
        double result = NumberUtil.doubleFmt(ll + (romLL * (Math.random() > 0.5D ? 1 : -1)), 8);
        return result + "";
    }

    static class LatLng {
        String lat;
        String lng;
        String aimStr;
    }
}