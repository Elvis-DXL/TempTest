package com.elvis.test.enums;

import java.util.List;

/**
 * 对象、集合标志枚举
 *
 * @author : Elvis
 * @since : 2021/12/21 13:51
 */
public enum AnnoEnum {
    //枚举定义
    Desensitization, Encryption, JsonTrans, ListMerge,

    //定义结束
    ;

    public boolean included(List<String> aimList) {
        if (null == aimList || aimList.size() == 0) {
            return false;
        }
        return aimList.contains(this.name());
    }
}
