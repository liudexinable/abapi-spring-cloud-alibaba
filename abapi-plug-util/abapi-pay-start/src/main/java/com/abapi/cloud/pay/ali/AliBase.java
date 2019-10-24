package com.abapi.cloud.pay.ali;

/**
 * @Author ldx
 * @Date 2019/9/30 13:52
 * @Description
 * @Version 1.0.0
 */
public class AliBase {

    public final static String CHARSET = "UTF-8";

    public final static String FORMAT = "json";

    /**正式环境**/
    public final static String PRO_URL = "https://openapi.alipay.com/gateway.do";

    /**沙箱环境**/
    public final static String SANDBOX_URL = "https://openapi.alipaydev.com/gateway.do";


    /**支付宝  各接口 相关的 ProductCode**/

    public final static String ALIPAY_TRADE_APP_PAY = "QUICK_MSECURITY_PAY";

    public final static String ALIPAY_TRADE_PAGE_PAY = "FAST_INSTANT_TRADE_PAY";

    public final static String ALIPAY_TRADE_WAP_PAY = "QUICK_WAP_WAY";
}
