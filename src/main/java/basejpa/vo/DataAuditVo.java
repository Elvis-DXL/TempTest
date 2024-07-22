package basejpa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 8:58
 */
@Data
@Schema(name = "DataAuditVo", description = "数据基础VO")
public class DataAuditVo extends BaseVo {
    @Schema(name = "createUser", description = "创建人ID")
    protected Long createUser;
    @Schema(name = "updateUser", description = "更新人ID")
    protected Long updateUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "createTime", description = "创建时间")
    protected LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(name = "updateTime", description = "更新时间")
    protected LocalDateTime updateTime;

    @Schema(name = "createUserName", description = "创建人姓名")
    protected String createUserName;
    @Schema(name = "updateUserName", description = "更新人姓名")
    protected String updateUserName;
}