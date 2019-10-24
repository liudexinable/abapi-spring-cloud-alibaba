package com.abapi.cloud.pay.wx.model;

import cn.hutool.crypto.digest.MD5;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class WxpayQueryReturn implements Serializable {

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

	private String device_info;

	private String openid;

	private String is_subscribe;

	private String trade_type;

	private String trade_state;

	private String bank_type;

	private String total_fee;

	private String fee_type;

	private String cash_fee;

	private String cash_fee_type;

	private String coupon_fee;

	private String coupon_count;

	private String coupon_batch_id_$n;

	private String coupon_id_$n;

	private String coupon_fee_$n;

	private String transaction_id;

	private String out_trade_no;
	/** 附加数据 */
	private String attach;

	private String time_end;

	private String trade_state_desc;

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
		if (!StringUtils.isEmpty(this.attach)) {
			sb.append("attach=");
			sb.append(this.attach);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.bank_type)) {
			sb.append("bank_type=");
			sb.append(this.bank_type);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.cash_fee)) {
			sb.append("cash_fee=");
			sb.append(this.cash_fee);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.cash_fee_type)) {
			sb.append("cash_fee_type=");
			sb.append(this.cash_fee_type);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.coupon_fee )) {
			sb.append("coupon_fee=");
			sb.append(this.coupon_fee);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.coupon_count )) {
			sb.append("coupon_count=");
			sb.append(this.coupon_count);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.coupon_batch_id_$n )) {
			sb.append("coupon_batch_id_$n=");
			sb.append(this.coupon_batch_id_$n);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.coupon_id_$n )) {
			sb.append("coupon_id_$n=");
			sb.append(this.coupon_id_$n);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.coupon_fee_$n )) {
			sb.append("coupon_fee_$n=");
			sb.append(this.coupon_fee_$n);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.device_info )) {
			sb.append("device_info=");
			sb.append(this.device_info);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.err_code )) {
			sb.append("err_code=");
			sb.append(this.err_code);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.err_code_des )) {
			sb.append("device_info=");
			sb.append(this.device_info);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.fee_type )) {
			sb.append("fee_type=");
			sb.append(this.fee_type);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.is_subscribe )) {
			sb.append("is_subscribe=");
			sb.append(this.is_subscribe);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.mch_id )) {
			sb.append("mch_id=");
			sb.append(this.mch_id);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.nonce_str )) {
			sb.append("nonce_str=");
			sb.append(this.nonce_str);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.openid )) {
			sb.append("openid=");
			sb.append(this.openid);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.out_trade_no )) {
			sb.append("out_trade_no=");
			sb.append(this.out_trade_no);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.result_code )) {
			sb.append("result_code=");
			sb.append(this.result_code);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.return_code )) {
			sb.append("return_code=");
			sb.append(this.return_code);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.return_msg )) {
			sb.append("return_msg=");
			sb.append(this.return_msg);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.time_end)) {
			sb.append("time_end=");
			sb.append(this.time_end);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.total_fee)) {
			sb.append("total_fee=");
			sb.append(this.total_fee);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.trade_state )) {
			sb.append("trade_state=");
			sb.append(this.trade_state);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.trade_type )) {
			sb.append("trade_type=");
			sb.append(this.trade_type);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.transaction_id)) {
			sb.append("transaction_id=");
			sb.append(this.transaction_id);
			sb.append("&");
		}
		if (!StringUtils.isEmpty(this.trade_state_desc)) {
			sb.append("trade_state_desc=");
			sb.append(this.trade_state_desc);
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

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
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

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getTrade_state_desc() {
		return trade_state_desc;
	}

	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public String getCoupon_fee() {
		return coupon_fee;
	}

	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public String getCoupon_count() {
		return coupon_count;
	}

	public void setCoupon_count(String coupon_count) {
		this.coupon_count = coupon_count;
	}

	public String getCoupon_batch_id_$n() {
		return coupon_batch_id_$n;
	}

	public void setCoupon_batch_id_$n(String coupon_batch_id_$n) {
		this.coupon_batch_id_$n = coupon_batch_id_$n;
	}

	public String getCoupon_id_$n() {
		return coupon_id_$n;
	}

	public void setCoupon_id_$n(String coupon_id_$n) {
		this.coupon_id_$n = coupon_id_$n;
	}

	public String getCoupon_fee_$n() {
		return coupon_fee_$n;
	}

	public void setCoupon_fee_$n(String coupon_fee_$n) {
		this.coupon_fee_$n = coupon_fee_$n;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}
