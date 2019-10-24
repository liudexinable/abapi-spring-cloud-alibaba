package com.abapi.cloud.pay.executor;

import com.abapi.cloud.pay.ali.AliBase;
import com.abapi.cloud.pay.ali.AliPayBizConfig;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * @Author ldx
 * @Date 2019/9/30 14:15
 * @Description
 * @Version 1.0.0
 */
public abstract class AbstractPayExecutor {

    protected AliPayBizConfig aliConfig;

    private static AlipayClient alipayClient;

    private static AlipayClient alipayClientSpecial;

    public AlipayClient getAlipayClient(){
        if(alipayClient == null){
            alipayClient = new DefaultAlipayClient(aliConfig.getAliSandbox() ? AliBase.SANDBOX_URL : AliBase.PRO_URL,
                    aliConfig.getAliAppId(),
                    aliConfig.getAliPrivateKey(),
                    AliBase.FORMAT,
                    AliBase.CHARSET,
                    aliConfig.getAliSignType().equals("RSA") ? aliConfig.getAliPublicKey() : aliConfig.getAliPublicKey256(),
                    aliConfig.getAliSignType());
        }
        return alipayClient;
    }

    public AlipayClient getAlipayClientSpecial(){
        if(alipayClient == null){
            alipayClient = new DefaultAlipayClient(aliConfig.getAliSandbox() ? AliBase.SANDBOX_URL : AliBase.PRO_URL,
                    aliConfig.getAliAppId(),
                    aliConfig.getAliPrivateKey(),
                    AliBase.FORMAT,
                    AliBase.CHARSET,
                    aliConfig.getAliPlatformPublicKey(),
                    aliConfig.getAliSignType());
        }
        return alipayClient;
    }


    public abstract void checkOpen();

    public abstract void checkSourceProperties();

}
