package com.elvis.test.demopg.vo.cmd;

import lombok.Data;
import com.elvis.test.base.InterfaceOfModifyBase;
import com.elvis.test.demopg.entity.Demo;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:55
 */
@Data
public class DemoModifyCmd implements InterfaceOfModifyBase<Long, Demo>, Serializable {

    @Override
    public Demo modifyIntoOldEntityObj(Demo oldObj) {
        return null;
    }

    @Override
    public Long obtainPK() {
        return null;
    }
}
