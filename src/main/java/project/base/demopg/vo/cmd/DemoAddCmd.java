package project.base.demopg.vo.cmd;

import lombok.Data;
import project.base.business.InterfaceOfAddBase;
import project.base.demopg.entity.Demo;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:55
 */
@Data
public class DemoAddCmd implements InterfaceOfAddBase<Demo>, Serializable {

    @Override
    public Demo createNewEntityObj() {
        return null;
    }
}
