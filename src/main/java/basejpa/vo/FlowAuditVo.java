package basejpa.vo;

import basejpa.enums.FlowBtn;
import basejpa.enums.FlowLink;
import basejpa.enums.FlowSAS;
import basejpa.enums.FlowTurn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 8:59
 */
@Data
public class FlowAuditVo extends DataAuditVo {
    @Schema(name = "auditStart", description = "流程开始")
    private Boolean auditStart;

    @Schema(name = "auditEnd", description = "流程审核结束")
    private Boolean auditEnd;

    @Schema(name = "startFlowLink", description = "流程开始环节")
    private FlowLink startFlowLink;

    @Schema(name = "currentFlowLink", description = "流程当前环节")
    private FlowLink currentFlowLink;

    @Schema(name = "flowStatus", description = "流程状态")
    private FlowSAS flowStatus;

    @Schema(name = "waitDealUserId", description = "待处理用户ID")
    private String waitDealUserId;

    @Schema(name = "waitDealUserName", description = "待处理用户名称")
    private String waitDealUserName;

    @Schema(name = "hisDealUserId", description = "历史处理人ID")
    private String hisDealUserId;

    @Schema(name = "hisDealUserName", description = "历史处理人名称")
    private String hisDealUserName;

    @Schema(name = "flowTurnJson", description = "流程流转JSON")
    private String flowTurnJson;

    @Schema(name = "turnInfos", description = "流程流转记录")
    private List<FlowTurn> turnInfos;

    @Schema(name = "flowBtn", description = "流程按钮")
    private FlowBtn flowBtn;
}