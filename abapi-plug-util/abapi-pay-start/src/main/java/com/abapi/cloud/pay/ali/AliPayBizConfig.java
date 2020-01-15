package com.abapi.cloud.pay.ali;

import lombok.Data;

/**
 * @Author ldx
 * @Date 2019/9/27 18:02
 * @Description
 * @Version 1.0.0
 */
@Data
public class AliPayBizConfig {

    /**是否打开支付宝支付**/
    private Boolean open;

    /**alipay app id**/
    private String aliAppId;

    /**alipay private key**/
    private String aliPrivateKey;

    /**alipay public key**/
    private String aliPublicKey;

    /**alipay 256 public key**/
    private String aliPublicKey256;

    /**alipay 256 public key**/
    private String aliPlatformPublicKey;

    /**alipay sign type [RSA|RSA2] default RSA2**/
    private String aliSignType = "RSA2";

    /**ali pay sandbox default false**/
    private Boolean aliSandbox;
}
