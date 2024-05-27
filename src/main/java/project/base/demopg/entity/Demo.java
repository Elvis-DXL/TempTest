package project.base.demopg.entity;

import lombok.Data;
import project.base.business.jpa.BaseBusiness;

import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:51
 */
@Data
public class Demo implements BaseBusiness.PKSet, Serializable {

    @Override
    public void newObjSetPK() {
    }
}
