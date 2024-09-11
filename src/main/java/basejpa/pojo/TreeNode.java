package basejpa.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/18 17:07
 */
@Data
public final class TreeNode implements Serializable {
    private String id;
    private String key;
    private String name;
    private String parentId;
    private List<TreeNode> children;
    private String type;
    private Object self;

    private TreeNode() {
    }

    public TreeNode selfId(String selfId) {
        setId(selfId);
        setKey(selfId);
        return this;
    }

    public TreeNode selfName(String selfName) {
        setName(selfName);
        return this;
    }

    public TreeNode selfType(String selfType) {
        setType(selfType);
        return this;
    }

    public TreeNode parentId(String parentId) {
        setParentId(parentId);
        return this;
    }

    public TreeNode selfObject(Object selfObject) {
        setSelf(selfObject);
        return this;
    }

    public static TreeNode newInstance() {
        return new TreeNode();
    }

    public static List<TreeNode> formatTree(List<TreeNode> treeNodeList) {
        if (null == treeNodeList || treeNodeList.isEmpty()) {
            return new ArrayList<>();
        }
        List<TreeNode> result = new ArrayList<>();
        Map<String, TreeNode> tmpMap = new HashMap<>();
        for (TreeNode treeNode : treeNodeList) {
            tmpMap.put(treeNode.getId(), treeNode);
        }
        List<TreeNode> childList;
        for (TreeNode item : treeNodeList) {
            if (null != item.getParentId() && !item.getParentId().equals("")
                    && null != tmpMap.get(item.getParentId()) && !item.getId().equals(item.getParentId())) {
                childList = null == tmpMap.get(item.getParentId()).getChildren() ? new ArrayList<>()
                        : tmpMap.get(item.getParentId()).getChildren();
                childList.add(item);
                tmpMap.get(item.getParentId()).setChildren(childList);
            } else {
                result.add(item);
            }
        }
        return result;
    }
}