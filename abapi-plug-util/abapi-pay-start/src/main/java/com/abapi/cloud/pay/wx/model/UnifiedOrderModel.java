package com.abapi.cloud.pay.wx.model;

import java.io.Serializable;


public class UnifiedOrderModel implements Serializable {
	private static final long serialVersionUID = 5836663895931344401L;

	private String body;

	private String out_trade_no;

	private String total_fee;

	private String spbill_create_ip;
//	@NotNull
//	private String attach;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

//	public String getAttach() {
//		return attach;
//	}
//
//	public void setAttach(String attach) {
//		this.attach = attach;
//	}

}
