package com.abapi.cloud.common.utils.UidGen;

import cn.hutool.core.lang.Singleton;

/**
 * @Author ldx
 * @Date 2019/11/22 11:44
 * @Description
 * @Version 1.0.0
 */
public class UidGenerateService {

    private volatile static DefaultUidGenerator defaultUidGenerator;

    private UidGenerateService(){}

    public static DefaultUidGenerator getTnitialize(long workerId){
        if (defaultUidGenerator == null) {
            synchronized (DefaultUidGenerator.class) {
                if (defaultUidGenerator == null) {
                    defaultUidGenerator = new DefaultUidGenerator(workerId);
                }
            }
        }
        return defaultUidGenerator;
    }

}
