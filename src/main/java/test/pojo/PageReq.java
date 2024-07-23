package test.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:05
 */
public class PageReq implements Serializable {
    protected Integer pageNum;
    protected Integer pageSize;
    protected List<OrderItem> orderList;

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public List<OrderItem> getOrderList() {
        return orderList;
    }

    public PageReq setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public PageReq setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageReq setOrderList(List<OrderItem> orderList) {
        this.orderList = orderList;
        return this;
    }
}