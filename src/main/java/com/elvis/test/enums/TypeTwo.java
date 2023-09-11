package com.elvis.test.enums;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/8 17:57
 */
public enum TypeTwo {
    TWO("沙发斯蒂芬"),
    FOUR("安防阿萨德"),
    ;

    TypeTwo(String fontTran) {
        this.fontTran = fontTran;
    }


    /********************************************************/
    private final String fontTran;

    public String fontTran() {
        return fontTran;
    }
    /********************************************************/
}
