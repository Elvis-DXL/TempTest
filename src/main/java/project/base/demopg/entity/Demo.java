package project.base.demopg.entity;

import lombok.Data;
import project.base.base.InterfaceOfDeleteBase;
import project.base.base.InterfaceOfEntityBase;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:51
 */
@Data
public class Demo implements InterfaceOfEntityBase, InterfaceOfDeleteBase, Serializable {

    private Integer id;

    private String name;

    private String exStr;

    @Column(name = "_delete", columnDefinition = "int(3) COMMENT '删除状态'")
    private Boolean delete = false;

    @Override
    public void deleteDealMark() {
        this.setDelete(Boolean.TRUE);
    }

    @Override
    public void newEntityObjSetPK() {
    }
}
