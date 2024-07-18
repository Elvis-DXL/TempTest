package com.elvis.test.base.demopg.vo.cmd;

import lombok.Data;
import com.elvis.test.base.base.DSUtil;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:56
 */
@Data
public class DemoListCmd extends DSUtil.PageReq implements Serializable {

    private String name;

    public DemoListCmd name(String name) {
        this.name = name;
        return this;
    }
}
