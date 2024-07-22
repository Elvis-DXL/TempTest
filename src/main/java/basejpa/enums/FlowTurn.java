package basejpa.enums;

import basejpa.pojo.FilePojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/2/19 10:54
 */
@Data
@Schema(name = "FlowTurn", description = "流程流转信息")
public class FlowTurn implements Serializable {
    @Schema(name = "flowLink", description = "流程环节")
    private String flowLink;

    @Schema(name = "flowSp", description = "流程审批")
    private FlowSAS flowSp;
    @Schema(name = "spMsg", description = "审批意见")
    private String spMsg;

    @Schema(name = "userId", description = "用户信息")
    private Long userId;
    @Schema(name = "userName", description = "用户名字")
    private String userName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "optTime", description = "操作时间")
    private LocalDateTime optTime;

    @Schema(name = "fileList", description = "审批附件信息")
    private List<FilePojo> fileList;
}