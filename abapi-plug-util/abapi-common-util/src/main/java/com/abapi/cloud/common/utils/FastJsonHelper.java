package com.abapi.cloud.common.utils;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.math.BigInteger;

/**
 * @Author ldx
 * @Date 2019/6/18 11:25
 * @Description
 * @Version 1.0.0
 */
public class FastJsonHelper {

    public static SerializeConfig fastConvertSerializeConfig(){
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        return serializeConfig;
    }

}
