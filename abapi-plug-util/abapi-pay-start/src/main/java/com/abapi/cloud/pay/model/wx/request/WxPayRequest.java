package com.abapi.cloud.pay.model.wx.request;

import lombok.Data;

/**
 * @Author ldx
 * @Date 2019/6/21 17:17
 * @Description
 * @Version 1.0.0
 */
@Data
public class WxPayRequest {

   private String outTradeNo;

   private String totalFee;

   private String body;

   private String attach;

   private String openId;

   private String callBack;

   private String timeExpire;

}
