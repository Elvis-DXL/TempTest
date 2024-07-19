package com.elvis.test.demopg.vo.cmd;

import basejpa.interfaces.AddBase;
import com.elvis.test.demopg.entity.Demo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:55
 */
@Data
public class DemoAddCmd implements AddBase<Demo>, Serializable {

    @Override
    public Demo createNewEntityObj() {
        return null;
    }
}
