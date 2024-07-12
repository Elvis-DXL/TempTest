package project.base.interfaces;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/12 10:22
 */
public interface ModifyBaseInterface<ID, EN> {
    ID obtainPK();

    EN modifyIntoOldEntityObj(EN oldObj);
}