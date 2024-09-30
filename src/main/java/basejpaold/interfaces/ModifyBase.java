package basejpaold.interfaces;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/19 14:35
 */
public interface ModifyBase<ID, EN> {
    ID obtainPrimaryKey();

    EN modifyIntoOldEntityObj(EN oldObj);
}