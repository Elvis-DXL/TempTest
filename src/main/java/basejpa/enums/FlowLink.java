package basejpa.enums;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/3 17:52
 */
public enum FlowLink {
    DEMO(1000, "样例", null, null, null, false, false),


    //定义结束
    ;
    private final int flowNum;
    //环节名字
    private final String linkName;
    //通过的下一步
    private final String agreeNext;
    //拒绝的下一步
    private final String rejectNext;
    //当前环节审核角色
    private final String auditRole;
    //当前环节能否通过
    private final Boolean canAgree;
    //当前环节能否驳回
    private final Boolean canReject;

    FlowLink(final int flowNum, final String linkName,
             final String agreeNext, final String rejectNext,
             final String auditRole,
             final Boolean canAgree, final Boolean canReject) {
        this.flowNum = flowNum;
        this.linkName = linkName;
        this.agreeNext = agreeNext;
        this.rejectNext = rejectNext;
        this.auditRole = auditRole;
        this.canAgree = canAgree;
        this.canReject = canReject;
    }

    public FlowLink nextLink(FlowSAS flowSp) {
        return getByName(FlowSAS.REJECT.equals(flowSp) ? this.rejectNext : this.agreeNext);
    }

    private static FlowLink getByName(String name) {
        if (null == name) {
            return null;
        }
        for (FlowLink it : FlowLink.values()) {
            if (it.name().equals(name)) {
                return it;
            }
        }
        return null;
    }

    public String auditRole() {
        return auditRole;
    }

    public Boolean canAgree() {
        return canAgree;
    }

    public Boolean canReject() {
        return canReject;
    }

    public String linkName() {
        return linkName;
    }
}