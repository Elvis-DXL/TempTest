package basejpa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/2/21 11:17
 */
@Data
@Schema(name = "FlowBtn", description = "流程按钮")
public class FlowBtn implements Serializable {
    @Schema(name = "agree", description = "通过")
    private Boolean agree = false;

    @Schema(name = "reject", description = "驳回")
    private Boolean reject = false;
}