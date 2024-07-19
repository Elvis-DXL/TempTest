package com.elvis.test.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:07
 */
public final class TreeNode implements Serializable {
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
        if (null == treeNodeList || treeNodeList.isEmpty()) {
            return new ArrayList<>();
        }
        List<TreeNode> result = new ArrayList<>();
        Map<String, TreeNode> tmpMap = new HashMap<>();
        for (TreeNode treeNode : treeNodeList) {
            tmpMap.put(treeNode.getSelfId(), treeNode);
        }
        List<TreeNode> childList;
        for (TreeNode item : treeNodeList) {
            if (null != item.getParentId() && !item.getParentId().equals("")
                    && null != tmpMap.get(item.getParentId()) && !item.getSelfId().equals(item.getParentId())) {
                childList = null == tmpMap.get(item.getParentId()).getChildList() ?
                        new ArrayList<>() : tmpMap.get(item.getParentId()).getChildList();
                childList.add(item);
                tmpMap.get(item.getParentId()).setChildList(childList);
            } else {
                result.add(item);
            }
        }
        return result;
    }
}