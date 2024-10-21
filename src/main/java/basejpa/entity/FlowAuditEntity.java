package basejpa.entity;

import basejpa.enums.FlowLink;
import basejpa.enums.FlowSAS;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 8:56
 */
@Data
@MappedSuperclass
public class FlowAuditEntity extends DataAuditEntity {
    @Column(name = "audit_start", columnDefinition = "int(3) COMMENT '流程开始'")
    protected Boolean auditStart = false;
    @Column(name = "audit_end", columnDefinition = "int(3) COMMENT '流程审核结束'")
    protected Boolean auditEnd = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "start_flow_link", columnDefinition = "varchar(128) COMMENT '流程开始环节'")
    protected FlowLink startFlowLink;
    @Enumerated(EnumType.STRING)
    @Column(name = "current_flow_link", columnDefinition = "varchar(128) COMMENT '流程当前环节'")
    protected FlowLink currentFlowLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "flow_status", columnDefinition = "varchar(64) COMMENT '流程状态'")
    protected FlowSAS flowStatus;

    @Column(name = "wait_deal_user_id", columnDefinition = "text COMMENT '待处理用户ID'")
    protected String waitDealUserId;
    @Column(name = "wait_deal_user_name", columnDefinition = "text COMMENT '待处理用户名称'")
    protected String waitDealUserName;
    @Column(name = "his_deal_user_id", columnDefinition = "text COMMENT '历史处理人ID'")
    protected String hisDealUserId;
    @Column(name = "his_deal_user_name", columnDefinition = "text COMMENT '历史处理人名称'")
    protected String hisDealUserName;

    @Column(name = "flow_turn_json", columnDefinition = "text COMMENT '流程流转JSON'")
    protected String flowTurnJson;
}