package basejpa.interfaces;

import basejpa.entity.FlowAuditEntity;
import basejpa.pojo.FlowTurn;
import basejpa.pojo.FlowSp;
import basejpa.pojo.SpUser;

import java.util.List;

import static basejpa.util.DSUtil.EmptyTool.isEmpty;
import static basejpa.util.DSUtil.Symbol.DH;
import static basejpa.util.DSUtil.*;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/7/22 14:01
 */
public interface FlowBase<EN extends FlowAuditEntity, EN_VO, CREATE_CMD, AUDIT_CMD> {

    EN_VO create(CREATE_CMD cmd);

    void audit(AUDIT_CMD cmd);

    List<SpUser> getFlowSpDealUsers(EN obj);

    default void flowPostProcessing(EN obj, FlowSp spPo, Long userId, String userName) {
        obj.setHisDealUserId(
                isEmpty(obj.getHisDealUserId()) ? DH.bothSidesAdd(userId) : (obj.getHisDealUserId() + userId + DH.val()));
        obj.setHisDealUserName(
                isEmpty(obj.getHisDealUserName()) ? userName : (obj.getHisDealUserName() + DH.val() + userName));
        List<FlowTurn> turns = parseList(obj.getFlowTurnJson(), FlowTurn.class);
        turns.add(FlowTurn.consTurnInfo(obj.getCurrentFlowLink().name(), spPo, userId, userName));
        obj.setFlowTurnJson(toJson(turns));
        obj.setFlowStatus(spPo.getFlowSp());
        obj.setCurrentFlowLink(obj.getCurrentFlowLink().nextLink(spPo.getFlowSp()));
        List<SpUser> users = getFlowSpDealUsers(obj);
        obj.setWaitDealUserId(DH.join(users, SpUser::getUserId));
        obj.setWaitDealUserName(DH.join(users, SpUser::getUserName, false));
        trueDo(null == obj.getCurrentFlowLink(), obj, it -> it.setAuditEnd(Boolean.TRUE));
        afterFlowPostDealBusiness(obj, spPo, userId, userName);
    }

    default void afterFlowPostDealBusiness(EN obj, FlowSp spPo, Long userId, String userName) {
    }
}