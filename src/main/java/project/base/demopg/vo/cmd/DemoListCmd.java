package project.base.demopg.vo.cmd;

import lombok.Data;

import java.io.Serializable;

import static project.base.base.DSUtil.PageReq;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:56
 */
@Data
public class DemoListCmd extends PageReq implements Serializable {

    private String name;

    public DemoListCmd name(String name) {
        this.name = name;
        return this;
    }
}
