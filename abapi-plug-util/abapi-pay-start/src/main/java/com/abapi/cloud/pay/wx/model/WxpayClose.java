package com.abapi.cloud.pay.wx.model;

import cn.hutool.crypto.digest.MD5;

import java.io.Serializable;

public class WxpayClose implements Serializable {

	private static final long serialVersionUID = -4499810251834034792L;

	/** 公众账号ID */
	private String appid;

	/** 商户号 */
	private String mch_id;

	/** 商户订单号 */
	private String out_trade_no;

	/** 随机字符串 */
	private String nonce_str;

	/** 签名 */
	private String sign;

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
		if (this.out_trade_no != null) {
			sb.append("out_trade_no=");
			sb.append(this.out_trade_no);
			sb.append("&");
		}
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
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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

}
