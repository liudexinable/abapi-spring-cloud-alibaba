package com.abapi.cloud.common.utils;

/**
 * @Author ldx
 * @Date 2019/9/24 10:19
 * @Description
 * @Version 1.0.0
 */
public class IdUtil {

    public static Long WORKERID = 1L;
    public static Long DATACENTERID = 1L;

    public static Long genId(){
        return cn.hutool.core.util.IdUtil.getSnowflake(WORKERID,DATACENTERID).nextId();
    }

    public static String genObjectId(){
        return cn.hutool.core.util.IdUtil.objectId();
    }

    public static String genFastSimpleUUID(){
        return cn.hutool.core.util.IdUtil.fastSimpleUUID();
    }

    public static String fenFastUUID(){
        return cn.hutool.core.util.IdUtil.fastUUID();
    }

}
