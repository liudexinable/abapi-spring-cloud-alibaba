package com.abapi.cloud.common.utils.UidGen;

/**
 * @Author ldx
 * @Date 2019/11/21 17:11
 * @Description
 * @Version 1.0.0
 */
public interface WorkerIdAssigner {

    /**
     * Assign worker id for {@link DefaultUidGenerator}
     *
     * @return assigned worker id
     */
    long assignWorkerId();


}
