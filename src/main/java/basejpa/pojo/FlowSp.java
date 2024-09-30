package basejpa.pojo;

import basejpa.enums.FlowSAS;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 14:03
 */
@Data
public class FlowSp implements Serializable {
    @Schema(name = "flowSp", description = "通过/驳回")
    private FlowSAS flowSp;
    @Schema(name = "spMsg", description = "审批意见")
    private String spMsg;
}