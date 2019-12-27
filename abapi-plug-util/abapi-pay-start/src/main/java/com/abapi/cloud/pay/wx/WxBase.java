package com.abapi.cloud.pay.wx;

/**
 * @Author ldx
 * @Date 2019/10/10 10:27
 * @Description
 * @Version 1.0.0
 */
public class WxBase {

    /** 交易类型(APP支付) **/
    public static final String WX_TRADE_TYPE_APP = "APP";
    /** 交易类型(微信公众号支付/小程序) **/
    public static final String WX_TRADE_TYPE_JSAPI = "JSAPI";
    /** 交易类型(PC网站支付) **/
    public static final String WX_TRADE_TYPE_NATIVE = "NATIVE";
    /** 交易类型(H5支付) **/
    public static final String WX_TRADE_TYPE_MWEB = "MWEB";

    /**返回值**/
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String OK = "OK";

    /**成功返回**/
    public static final String RETURN_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";

    /**正式环境**/
    /** 统一下单接口 **/
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /** 查询订单接口 **/
    public static final String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /** 关闭订单接口 **/
    public static final String CLOSE_ORDER_URl = "https://api.mch.weixin.qq.com/pay/closeorder";
    /** 退款订单接口 **/
    public static final String REFUND_ORDER_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**沙箱环境**/
    /**获取沙箱sign**/
    public static final String SIGN_KEY_SANDBOX_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
    /**统一下单接口**/
    public static final String UNIFIED_ORDER_SANDBOX_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
    /** 查询订单接口 **/
    public static final String QUERY_ORDER_SANDBOX_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";
    /** 关闭订单接口 **/
    public static final String CLOSE_ORDER_SANDBOX_URl = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";
    /** 退款订单接口 **/
    public static final String REFUND_ORDER_SANDBOX_URL = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/refund";
}
