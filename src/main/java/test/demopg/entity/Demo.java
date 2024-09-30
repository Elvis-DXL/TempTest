package test.demopg.entity;

import basejpa.interfaces.DeleteBase;
import basejpa.interfaces.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/5/27 13:51
 */
@Data
public class Demo implements EntityBase, DeleteBase, Serializable {

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
    public void newEntityObjSetPrimaryKey() {

    }
}
