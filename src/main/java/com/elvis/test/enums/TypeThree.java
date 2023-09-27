package com.elvis.test.enums;

import com.elvis.test.anno.FontTransMethod;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/8 17:57
 */
@FontTransMethod(method = "fontTran")
public enum TypeThree {
    TWO("沙发斯蒂芬"),
    FOUR("安防阿萨德"),
    ;

    TypeThree(String fontTran) {
        this.fontTran = fontTran;
    }


    /********************************************************/
    private final String fontTran;

    public String fontTran() {
        return fontTran;
    }
    /********************************************************/
}
