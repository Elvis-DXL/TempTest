package basejpa.vo;

import basejpa.enums.FlowLink;
import basejpa.enums.FlowSAS;
import basejpa.pojo.FlowBtn;
import basejpa.pojo.FlowTurn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 8:59
 */
@Data
@Schema(name = "FlowAuditVo", description = "流程数据基础VO")
public class FlowAuditVo extends DataAuditVo {
    @Schema(name = "auditStart", description = "流程开始")
    protected Boolean auditStart;
    @Schema(name = "auditEnd", description = "流程审核结束")
    protected Boolean auditEnd;

    @Schema(name = "startFlowLink", description = "流程开始环节")
    protected FlowLink startFlowLink;
    @Schema(name = "currentFlowLink", description = "流程当前环节")
    protected FlowLink currentFlowLink;

    @Schema(name = "flowStatus", description = "流程状态")
    protected FlowSAS flowStatus;

    @Schema(name = "waitDealUserId", description = "待处理用户ID")
    protected String waitDealUserId;
    @Schema(name = "waitDealUserName", description = "待处理用户名称")
    protected String waitDealUserName;
    @Schema(name = "hisDealUserId", description = "历史处理人ID")
    protected String hisDealUserId;
    @Schema(name = "hisDealUserName", description = "历史处理人名称")
    protected String hisDealUserName;

    @Schema(name = "flowTurnJson", description = "流程流转JSON")
    protected String flowTurnJson;
    @Schema(name = "turnInfos", description = "流程流转记录")
    protected List<FlowTurn> turnInfos;

    @Schema(name = "flowBtn", description = "流程按钮")
    protected FlowBtn flowBtn;
}