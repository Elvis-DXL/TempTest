package basejpa.handle;

import basejpa.entity.DataAuditEntity;
import basejpa.entity.FlowAuditEntity;
import basejpa.enums.FlowTurn;
import basejpa.vo.DataAuditVo;
import basejpa.vo.FlowAuditVo;

import static basejpa.util.DSUtil.objGet;
import static basejpa.util.DSUtil.parseList;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 9:03
 */
public final class DataTool {
    private DataTool() {
        throw new AssertionError("Utility classes do not allow instantiation");
    }

    public static <T extends DataAuditEntity, K extends DataAuditVo> void dataAuditDeal(T srcObj, K aimObj) {
        aimObj.setId(objGet(srcObj, DataAuditEntity::getId));
        aimObj.setCreateUser(objGet(srcObj, DataAuditEntity::getCreateUser));
        aimObj.setCreateTime(objGet(srcObj, DataAuditEntity::getCreateTime));
        aimObj.setUpdateUser(objGet(srcObj, DataAuditEntity::getUpdateUser));
        aimObj.setUpdateTime(objGet(srcObj, DataAuditEntity::getUpdateTime));
    }

    public static <T extends FlowAuditEntity, K extends FlowAuditVo> void flowAuditDeal(T srcObj, K aimObj) {
        dataAuditDeal(srcObj, aimObj);
        aimObj.setAuditStart(objGet(srcObj, FlowAuditEntity::getAuditStart));
        aimObj.setAuditEnd(objGet(srcObj, FlowAuditEntity::getAuditEnd));
        aimObj.setStartFlowLink(objGet(srcObj, FlowAuditEntity::getStartFlowLink));
        aimObj.setCurrentFlowLink(objGet(srcObj, FlowAuditEntity::getCurrentFlowLink));
        aimObj.setFlowStatus(objGet(srcObj, FlowAuditEntity::getFlowStatus));
        aimObj.setWaitDealUserId(objGet(srcObj, FlowAuditEntity::getWaitDealUserId));
        aimObj.setWaitDealUserName(objGet(srcObj, FlowAuditEntity::getWaitDealUserName));
        aimObj.setHisDealUserId(objGet(srcObj, FlowAuditEntity::getHisDealUserId));
        aimObj.setHisDealUserName(objGet(srcObj, FlowAuditEntity::getHisDealUserName));
        aimObj.setFlowTurnJson(objGet(srcObj, FlowAuditEntity::getFlowTurnJson));
        aimObj.setTurnInfos(parseList(objGet(srcObj, FlowAuditEntity::getFlowTurnJson), FlowTurn.class));
    }
}