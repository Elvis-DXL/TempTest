package project.base.demopg.vo.cmd;

import lombok.Data;
import project.base.business.jpa.BaseBusiness;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:55
 */
@Data
public class DemoModifyCmd implements BaseBusiness.PKGet<Long>, Serializable {

    @Override
    public Long getPK() {
        return null;
    }
}
