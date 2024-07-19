package com.elvis.test.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:06
 */
public final class PageResp<T> implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalNum;
    private Integer totalPage;
    private List<T> dataList;

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public PageResp<T> setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public PageResp<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageResp<T> setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public PageResp<T> setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public PageResp<T> setDataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }
}