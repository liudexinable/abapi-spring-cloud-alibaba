package com.abapi.cloud.pay.model.wx.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxPayResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -227502969307419391L;

	// 返回码
	private String code;

	// 返回错误码
	private String errorCode;

	// 错误日志
	private String errorMsg;

	// 微信appId
	private String appId;

	// 订单编号
	private String outTradeNo;

	// 金额
	private String totalAmount;

	// 微信支付的微信账单Id
	private String prepayId;

	// 随机因子
	private String nonceStr;

	// 签名
	private String sign;

	// 时间戳
	private String timeStamp;

	private String packageSign = "Sign=WXPay";

	// 商户号
	private String mchId;

	// 二维码字符串
	private String QRStr;

	// 公众号支付 签名
	private String paySign;

}
