package com.abapi.cloud.pay.wx;

import lombok.Data;

import java.util.Map;

/**
 * @Author ldx
 * @Date 2019/9/30 13:17
 * @Description
 * @Version 1.0.0
 */
@Data
public class WxPayBizConfig {

    /**是否打开微信支付**/
    private Boolean open;

    private Map<String,WxPayConfig> wxPayConfigs;

    @Data
    public static class WxPayConfig{
        /**type [APP,JSAPI,NATIVE,MWEB]**/
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
        /**wx sandbox**/
        private Boolean wxSandbox = false;
    }
}
