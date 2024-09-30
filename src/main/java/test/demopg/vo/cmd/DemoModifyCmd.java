package test.demopg.vo.cmd;

import basejpaold.interfaces.ModifyBase;
import test.demopg.entity.Demo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:55
 */
@Data
public class DemoModifyCmd implements ModifyBase<Long, Demo>, Serializable {

    @Override
    public Demo modifyIntoOldEntityObj(Demo oldObj) {
        return null;
    }

    @Override
    public Long obtainPrimaryKey() {
        return null;
    }
}
