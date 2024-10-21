package basejpa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 8:58
 */
@Data
@Schema(name = "BaseVo", description = "基础VO")
public class BaseVo implements Serializable {
    @Schema(name = "id", description = "ID主键")
    protected Long id;
}