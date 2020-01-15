package com.abapi.cloud.pay.model.wx.request;

import lombok.Data;

/**
 * @Author ldx
 * @Date 2019/6/21 17:17
 * @Description
 * @Version 1.0.0
 */
@Data
public class WxRefundRequest {

   private String outTradeNo;

   private String tradeNo;

   private String totalFee;

   private String returnFee;

   private String outRequesrNo;

 //  private WxType type;

}
