package com.elvis.test.base.base;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/31 15:44
 */
public class ImEx implements Serializable {
    private Class<?> clazz;
    private String fileName;
    private String sheetName;

    private ImEx() {
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public static ImEx getInstance() {
        return new ImEx();
    }

    public ImEx clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ImEx fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ImEx sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }
}
