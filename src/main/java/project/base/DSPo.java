package project.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static project.base.DSUtil.EmptyTool.isEmpty;
import static project.base.DSUtil.EmptyTool.isNotEmpty;
import static project.base.DSUtil.ListTool.listToMap;
import static project.base.DSUtil.mapGet;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/3 10:25
 */
public final class DSPo {
    private DSPo() {
        throw new AssertionError("DSPo do not allow instantiation");
    }

    public static class PageReq implements Serializable {
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

    public final static class PageResp<T> implements Serializable {
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

    public final static class OrderItem implements Serializable {
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

    public final static class TreeNode implements Serializable {
        private String selfId;
        private String selfName;
        private String selfType;
        private String parentId;
        private Object selfObject;
        private List<TreeNode> childList;

        private TreeNode() {
        }

        public String getSelfId() {
            return selfId;
        }

        public String getSelfName() {
            return selfName;
        }

        public String getSelfType() {
            return selfType;
        }

        public String getParentId() {
            return parentId;
        }

        public Object getSelfObject() {
            return selfObject;
        }

        public List<TreeNode> getChildList() {
            return childList;
        }

        public void setSelfId(String selfId) {
            this.selfId = selfId;
        }

        public void setSelfName(String selfName) {
            this.selfName = selfName;
        }

        public void setSelfType(String selfType) {
            this.selfType = selfType;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public void setSelfObject(Object selfObject) {
            this.selfObject = selfObject;
        }

        public void setChildList(List<TreeNode> childList) {
            this.childList = childList;
        }

        public TreeNode selfId(String selfId) {
            setSelfId(selfId);
            return this;
        }

        public TreeNode selfName(String selfName) {
            setSelfName(selfName);
            return this;
        }

        public TreeNode selfType(String selfType) {
            setSelfType(selfType);
            return this;
        }

        public TreeNode parentId(String parentId) {
            setParentId(parentId);
            return this;
        }

        public TreeNode selfObject(Object selfObject) {
            setSelfObject(selfObject);
            return this;
        }

        public static TreeNode getInstance() {
            return new TreeNode();
        }

        public static List<TreeNode> formatTree(List<TreeNode> treeNodeList) {
            if (isEmpty(treeNodeList)) {
                return new ArrayList<>();
            }
            List<TreeNode> result = new ArrayList<>();
            Map<String, TreeNode> tmpMap = listToMap(treeNodeList, TreeNode::getSelfId, it -> it);
            List<TreeNode> childList;
            for (TreeNode item : treeNodeList) {
                if (isNotEmpty(item.getParentId()) && null != mapGet(tmpMap, item.getParentId())
                        && !item.getSelfId().equals(item.getParentId())) {
                    childList = null == mapGet(tmpMap, item.getParentId()).getChildList() ?
                            new ArrayList<>() : mapGet(tmpMap, item.getParentId()).getChildList();
                    childList.add(item);
                    mapGet(tmpMap, item.getParentId()).setChildList(childList);
                } else {
                    result.add(item);
                }
            }
            return result;
        }
    }

    public final static class R<T> implements Serializable {
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
}