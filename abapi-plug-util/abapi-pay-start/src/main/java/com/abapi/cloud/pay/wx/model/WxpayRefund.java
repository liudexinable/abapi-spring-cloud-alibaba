package com.abapi.cloud.pay.wx.model;

import cn.hutool.crypto.digest.MD5;

public class WxpayRefund {

	// appid
	private String appid;

	// 商户号
	private String mch_id;

	// 随机字符串
	private String nonce_str;

	// 签名
	private String sign;

	// 微信订单号
	private String transaction_id;

	// 商户订单号
	private String out_trade_no;

	// 商户退款单号
	private String out_refund_no;

	// 订单金额
	private String total_fee;

	// 退款金额
	private String refund_fee;

	// 操作员
	private String op_user_id;
	
	/**
	 * 生成xml字符串
	 * 
	 * @return
	 */
	public String genXml(String signKey) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		if (this.appid != null) {
			sb.append("<appid><![CDATA[");
			sb.append(this.appid);
			sb.append("]]></appid>");
		}
		if (this.mch_id != null) {
			sb.append("<mch_id><![CDATA[");
			sb.append(this.mch_id);
			sb.append("]]></mch_id>");
		}
		if (this.nonce_str != null) {
			sb.append("<nonce_str><![CDATA[");
			sb.append(this.nonce_str);
			sb.append("]]></nonce_str>");
		}
		if (this.out_trade_no != null) {
			sb.append("<out_trade_no><![CDATA[");
			sb.append(this.out_trade_no);
			sb.append("]]></out_trade_no>");
		}
		if (this.transaction_id != null) {
			sb.append("<transaction_id><![CDATA[");
			sb.append(this.transaction_id);
			sb.append("]]></transaction_id>");
		}
		
		if(this.out_refund_no != null){
			sb.append("<out_refund_no><![CDATA[");
			sb.append(this.out_refund_no);
			sb.append("]]></out_refund_no>");
		}
		
		if(this.total_fee != null){
			sb.append("<total_fee><![CDATA[");
			sb.append(this.total_fee);
			sb.append("]]></total_fee>");
		}
		
		if(this.refund_fee != null){
			sb.append("<refund_fee><![CDATA[");
			sb.append(this.refund_fee);
			sb.append("]]></refund_fee>");
		}
		
		if(this.op_user_id != null){
			sb.append("<op_user_id><![CDATA[");
			sb.append(this.op_user_id);
			sb.append("]]></op_user_id>");
		}
		// 签名
		sign(signKey);
		sb.append("<sign><![CDATA[");
		sb.append(this.sign);
		sb.append("]]></sign>");

		sb.append("</xml>");

		return sb.toString();
	}
	
	/**
	 * 生成签名字符串
	 */
	public void sign(String signKey) {
		StringBuilder sb = new StringBuilder();
		if (this.appid != null) {
			sb.append("appid=");
			sb.append(this.appid);
			sb.append("&");
		}
		if (this.mch_id != null) {
			sb.append("mch_id=");
			sb.append(this.mch_id);
			sb.append("&");
		}
		if (this.nonce_str != null) {
			sb.append("nonce_str=");
			sb.append(this.nonce_str);
			sb.append("&");
		}
		if(this.op_user_id != null){
			sb.append("op_user_id=");
			sb.append(this.op_user_id);
			sb.append("&");
		}
		if(this.out_refund_no != null){
			sb.append("out_refund_no=");
			sb.append(this.out_refund_no);
			sb.append("&");
		}
		if (this.out_trade_no != null) {
			sb.append("out_trade_no=");
			sb.append(this.out_trade_no);
			sb.append("&");
		}
		if(this.refund_fee != null){
			sb.append("refund_fee=");
			sb.append(this.refund_fee);
			sb.append("&");
		}
		if(this.total_fee != null){
			sb.append("total_fee=");
			sb.append(this.total_fee);
			sb.append("&");
		}
		if (this.transaction_id != null) {
			sb.append("transaction_id=");
			sb.append(this.transaction_id);
			sb.append("&");
		}
		System.out.println(sb.toString());
		sb.append("key=");
		sb.append(signKey);
		this.sign = MD5.create().digestHex(sb.toString(), "UTF-8").toUpperCase();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
		// 操作员 默认 商户号
		this.op_user_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

}
