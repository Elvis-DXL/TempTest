package project.base.business;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/12 10:22
 */
public interface InterfaceOfModifyBase<ID, EN> {
    ID obtainPK();

    EN modifyIntoOldEntityObj(EN oldObj);
}