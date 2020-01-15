package com.abapi.cloud.common.utils.UidGen;

/**
 * @Author ldx
 * @Date 2019/11/21 17:12
 * @Description
 * @Version 1.0.0
 */
public interface UidGenerator {

    /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    String getUID(String code);

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);

}
