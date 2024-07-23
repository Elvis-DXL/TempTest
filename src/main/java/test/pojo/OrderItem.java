package test.pojo;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:05
 */
public final class OrderItem implements Serializable {
    private String column;
    private boolean asc = true;

    public OrderItem() {
    }

    public OrderItem(String column, boolean asc) {
        this.column = column;
        this.asc = asc;
    }

    public String getColumn() {
        return column;
    }

    public boolean isAsc() {
        return asc;
    }

    public OrderItem setColumn(String column) {
        this.column = column;
        return this;
    }

    public OrderItem setAsc(boolean asc) {
        this.asc = asc;
        return this;
    }
}