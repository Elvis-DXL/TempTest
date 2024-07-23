package test.pojo;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:09
 */
public final class R<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> data(T data) {
        return new R<T>().setCode(200).setData(data).setMsg("操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return new R<T>().setCode(200).setData(data).setMsg(msg);
    }

    public static <T> R<T> data(int code, T data, String msg) {
        return new R<T>().setCode(code).setData(data).setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public R<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }
}