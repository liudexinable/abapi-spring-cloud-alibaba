package com.abapi.cloud.pay.wx;

import com.abapi.cloud.pay.model.wx.request.WxPayRequest;
import com.abapi.cloud.pay.model.wx.request.WxRefundRequest;
import com.abapi.cloud.pay.model.wx.response.WxPayResponse;
import com.abapi.cloud.pay.wx.model.WxpayQueryReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author ldx
 * @Date 2019/10/10 11:41
 * @Description
 * @Version 1.0.0
 */
@Component
public class WxPayApi {

    @Autowired
    private WxPayExecutor wxPayExecutor;

    public static WxPayApi wxPayApi;

    @PostConstruct
    public void init(){
        wxPayApi = this;
        wxPayApi.wxPayExecutor = wxPayExecutor;
    }

    public static WxPayResponse toPay(WxPayRequest request,WxPayTrade trade){
        return wxPayApi.wxPayExecutor.toPay(request,trade);
    }

    public static String toRefund(WxRefundRequest request, WxPayTrade trade){
        return wxPayApi.wxPayExecutor.toRefund(request,trade);
    }

    public static WxpayQueryReturn orderQuery(String outTradeNo, WxPayTrade trade){
        return wxPayApi.wxPayExecutor.orderQuery(outTradeNo,trade);
    }
}
