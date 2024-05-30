package com.elvis.test.pojo;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/30 15:17
 */
public class R<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    private R() {
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> R<T> data() {
        return new R<>();
    }

    public static <T> R<T> data(T data) {
        return data(200, data, "操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return data(200, data, msg);
    }

    public static <T> R<T> data(int code, T data, String msg) {
        return new R<>(code, data, msg);
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public R<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}