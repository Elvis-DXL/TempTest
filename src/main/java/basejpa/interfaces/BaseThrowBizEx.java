package basejpa.interfaces;

import com.zx.core.base.exception.BizException;

public interface BaseThrowBizEx {
    default void throwBizEx(String msgStr) {
        throw bizEx(msgStr);
    }

    default BizException bizEx(String msgStr) {
        return new BizException(msgStr);
    }
}