package com.abapi.cloud.pay.wx.model;

import cn.hutool.crypto.digest.MD5;

import java.io.Serializable;


public class UnifiedOrderReturn implements Serializable {
	private static final long serialVersionUID = 5836663895931344401L;

	private String return_code;
	private String return_msg;
	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String result_code;
	private String err_code;
	private String err_code_des;
	private String trade_type;
	private String prepay_id;
	private String code_url;

	/**
	 * 签名校验
	 */
	public boolean signVerify(String signKey) {
		StringBuilder sb = new StringBuilder();
		if (this.appid != null) {
			sb.append("appid=");
			sb.append(this.appid);
			sb.append("&");
		}
		if (this.code_url != null) {
			sb.append("code_url=");
			sb.append(this.code_url);
			sb.append("&");
		}
		if (this.device_info != null) {
			sb.append("device_info=");
			sb.append(this.device_info);
			sb.append("&");
		}
		if (this.err_code != null) {
			sb.append("err_code=");
			sb.append(this.err_code);
			sb.append("&");
		}
		if (this.err_code_des != null) {
			sb.append("err_code_des=");
			sb.append(this.err_code_des);
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
		if (this.prepay_id != null) {
			sb.append("prepay_id=");
			sb.append(this.prepay_id);
			sb.append("&");
		}
		if (this.result_code != null) {
			sb.append("result_code=");
			sb.append(this.result_code);
			sb.append("&");
		}
		if (this.return_code != null) {
			sb.append("return_code=");
			sb.append(this.return_code);
			sb.append("&");
		}
		if (this.return_msg != null) {
			sb.append("return_msg=");
			sb.append(this.return_msg);
			sb.append("&");
		}
		if (this.trade_type != null) {
			sb.append("trade_type=");
			sb.append(this.trade_type);
			sb.append("&");
		}
		sb.append("key=");
		sb.append(signKey);
		String sign = MD5.create().digestHex(sb.toString(), "UTF-8").toUpperCase();

		if (this.sign != null && this.sign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
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

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

}
