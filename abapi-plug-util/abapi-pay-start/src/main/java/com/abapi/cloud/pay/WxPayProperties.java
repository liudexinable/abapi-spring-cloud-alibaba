package com.abapi.cloud.pay;

import lombok.Data;

/**
 * @Author ldx
 * @Date 2019/9/27 18:16
 * @Description
 * @Version 1.0.0
 */
@Data
public class WxPayProperties {

    /**type [JSAPI,NATIVE,MICROPAY,MWEB]**/
    private String tradeType;
    /**ip**/
    private String wxIp;
    /**app id**/
    private String wxAppId;
    /**mch id**/
    private String wxMchId;
    /**secrect**/
    private String wxSecret;
    /**cert path**/
    private String wxCertPath12;
    /**wxSandbox default false**/
    private Boolean wxSandbox = false;
}
