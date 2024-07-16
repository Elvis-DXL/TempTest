package project.base.demopg.vo.cmd;

import lombok.Data;
import project.base.interfaces.PKGet;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:55
 */
@Data
public class DemoModifyCmd implements PKGet<Long>, Serializable {

    @Override
    public Long obtainPK() {
        return null;
    }
}
