package com.abapi.cloud.pay.wx.model;

import cn.hutool.crypto.digest.MD5;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class WxpayCloseReturn implements Serializable {

	private static final long serialVersionUID = -4499810251834034792L;

	private String return_code;

	private String return_msg;
	/** 公众账号ID */
	private String appid;

	private String mch_id;

	private String nonce_str;

	private String sign;

	private String result_code;

	private String err_code;

	private String err_code_des;

	/**
	 * 生成签名字符串
	 */
	public boolean signVerify(String signKey) {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(this.appid)) {
			sb.append("appid=");
			sb.append(this.appid);
			sb.append("&");
		}

		if (!StringUtils.isEmpty(this.err_code)) {
			sb.append("err_code=");
			sb.append(this.err_code);
			sb.append("&");
		}

		if (!StringUtils.isEmpty(this.mch_id)) {
			sb.append("mch_id=");
			sb.append(this.mch_id);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.nonce_str)) {
			sb.append("nonce_str=");
			sb.append(this.nonce_str);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.result_code)) {
			sb.append("result_code=");
			sb.append(this.result_code);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.return_code)) {
			sb.append("return_code=");
			sb.append(this.return_code);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.return_msg)) {
			sb.append("return_msg=");
			sb.append(this.return_msg);
			sb.append("&");
		}
		sb.append("key=");
		sb.append(signKey);
		String sign = MD5.create().digestHex(sb.toString(), "UTF-8").toUpperCase();

		if (!StringUtils.isEmpty(this.sign) && this.sign.equals(sign)) {
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

}
