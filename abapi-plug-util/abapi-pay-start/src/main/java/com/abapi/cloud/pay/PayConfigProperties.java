package com.abapi.cloud.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * @Author ldx
 * @Date 2019/9/27 18:11
 * @Description
 * @Version 1.0.0
 */
@Data
@ConfigurationProperties(PayConfigProperties.PREFIX)
public class PayConfigProperties {

    public static final String PREFIX = "abapi.cloud.pay";

    /**open ali pay**/
    private Boolean aliEnabled = true;

    /**open wx pay**/
    private Boolean wxEnabled = true;

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
    private Boolean aliSandbox = false;

    /**wxpay properties**/
    private List<WxPayProperties> wxProperties;

}
