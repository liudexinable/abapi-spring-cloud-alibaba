package com.abapi.cloud.pay.model.ali.response;

import com.alipay.api.internal.mapping.ApiField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ldx
 * @Date 2019/10/9 16:01
 * @Description
 * @Version 1.0.0
 */
@Data
public class AliTradePayResponse implements Serializable {

    private String subCode;

    private String subMsg;

    private String body;

    private String outTradeNo;

    /**
     * 收款支付宝账号对应的支付宝唯一用户号。
     以2088开头的纯16位数字
     */
    private String sellerId;

    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     */
    private String totalAmount;

    /**
     * 该交易在支付宝系统中的交易流水号。
     */
    private String tradeNo;

}
