package project.base.demopg.entity;

import lombok.Data;
import project.base.interfaces.PKSet;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:51
 */
@Data
public class Demo implements PKSet, Serializable {

    private Integer id;

    private String name;

    private String exStr;

    @Override
    public void newObjSetPK() {
    }
}
