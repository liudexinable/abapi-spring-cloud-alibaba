package com.abapi.cloud.pay.ali;

import com.abapi.cloud.pay.model.ali.request.AliPayRefundRequest;
import com.abapi.cloud.pay.model.ali.request.AliPayRequest;
import com.abapi.cloud.pay.model.ali.request.AliPayTransferRequest;
import com.abapi.cloud.pay.model.ali.response.AliTradePayResponse;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author ldx
 * @Date 2019/9/30 16:40
 * @Description
 * @Version 1.0.0
 */
@Component
public class AliPayApi {

    @Autowired
    private AliPayExecutor aliPayExecutor;

    public static AliPayApi aliPayApi;

    @PostConstruct
    public void init() {
        aliPayApi = this;
        aliPayApi.aliPayExecutor = aliPayExecutor;
    }

    public static AliTradePayResponse toPay(AliPayRequest aliPayRequest,AliPayTrade trade) {
        return aliPayApi.aliPayExecutor.toPay(aliPayRequest,trade);
    }

    /**
     * 退款调用
     * @param aliPayRefundRequest
     * @return
     */
    public static Boolean toRefund(AliPayRefundRequest aliPayRefundRequest){
        return aliPayApi.aliPayExecutor.toRefund(aliPayRefundRequest);
    }

    /**
     * 异步验证 并返回 回调参数
     * @param request
     * @return
     */
    public static Map<String,String> asyCheckReturnParams(HttpServletRequest request){
        return aliPayApi.aliPayExecutor.asyCheckReturnParams(request);
    }

    /**
     * 企业转账到支付宝账户
     * @param aliPayTransferRequest
     * @return
     */
    public static Boolean toTransfer(AliPayTransferRequest aliPayTransferRequest){
        return aliPayApi.aliPayExecutor.toTransfer(aliPayTransferRequest);
    }

    /**
     * 获取企业转账到支付宝账户 信息
     * @param outBizNo
     * @return
     * @throws AlipayApiException
     */
    public static String toQueryTransfer(String outBizNo) throws AlipayApiException {
        return aliPayApi.aliPayExecutor.toQueryTransfer(outBizNo);
    }

    /**
     * 查询交易状态
     * @param outTradeNo
     * @return
     */
    public static AlipayTradeQueryResponse query(String outTradeNo){
        return aliPayApi.aliPayExecutor.query(outTradeNo);
    }
}
