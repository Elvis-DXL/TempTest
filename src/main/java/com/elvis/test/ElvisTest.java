package com.elvis.test;


import com.elvis.commons.utils.StrUtil;
import com.elvis.test.bean.Aim;
import com.zx.core.tool.utils.JsonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Elvis
 * @since : 2022/4/28 11:35
 */
public class ElvisTest {
    public static void main(String[] args) {
        List<Aim> aimList = new ArrayList<>();
        aimList.add(new Aim(1,"121","121231"));
        aimList.add(new Aim(2,"vasds","dfasd"));
        aimList.add(new Aim(3,"dfas","sva"));
        aimList.add(new Aim(4,"vav","asdfasf"));

        List list = JsonUtil.parse(JsonUtil.toJson(aimList),List.class);
        System.out.println(list);

    }
}