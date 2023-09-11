package com.elvis.test.enums;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2023/9/8 17:57
 */
public enum TypeOne {
    ONE("大声道"),
    THREE("阿斯顿发山东"),
    FIVE("代发阿道夫代发"),
    ;


    TypeOne(String fontTran) {
        this.fontTran = fontTran;
    }

    /********************************************************/
    private final String fontTran;

    public String fontTran() {
        return fontTran;
    }
    /********************************************************/
}
