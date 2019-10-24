package com.abapi.cloud.pay.wx.model;

import cn.hutool.crypto.digest.MD5;

import java.io.Serializable;


public class UnifiedOrder implements Serializable {
	private static final long serialVersionUID = 5836663895931344401L;

	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String body;
	private String detail;
	private String attach;
	private String out_trade_no;
	private String fee_type;
	private String total_fee;
	private String spbill_create_ip;
	private String time_start;
	private String time_expire;
	private String goods_tag;
	private String notify_url;
	private String trade_type;
	private String product_id;
	private String limit_pay;
	private String openid;

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
		if (this.device_info != null) {
			sb.append("<device_info><![CDATA[");
			sb.append(this.device_info);
			sb.append("]]></device_info>");
		}
		if (this.nonce_str != null) {
			sb.append("<nonce_str><![CDATA[");
			sb.append(this.nonce_str);
			sb.append("]]></nonce_str>");
		}
		if (this.body != null) {
			sb.append("<body><![CDATA[");
			sb.append(this.body);
			sb.append("]]></body>");
		}
		if (this.detail != null) {
			sb.append("<detail><![CDATA[");
			sb.append(this.detail);
			sb.append("]]></detail>");
		}
		if (this.attach != null) {
			sb.append("<attach><![CDATA[");
			sb.append(this.attach);
			sb.append("]]></attach>");
		}
		if (this.out_trade_no != null) {
			sb.append("<out_trade_no><![CDATA[");
			sb.append(this.out_trade_no);
			sb.append("]]></out_trade_no>");
		}
		if (this.fee_type != null) {
			sb.append("<fee_type><![CDATA[");
			sb.append(this.fee_type);
			sb.append("]]></fee_type>");
		}
		if (this.total_fee != null) {
			sb.append("<total_fee><![CDATA[");
			sb.append(this.total_fee);
			sb.append("]]></total_fee>");
		}
		if (this.spbill_create_ip != null) {
			sb.append("<spbill_create_ip><![CDATA[");
			sb.append(this.spbill_create_ip);
			sb.append("]]></spbill_create_ip>");
		}
		if (this.time_start != null) {
			sb.append("<time_start><![CDATA[");
			sb.append(this.time_start);
			sb.append("]]></time_start>");
		}
		if (this.time_expire != null) {
			sb.append("<time_expire><![CDATA[");
			sb.append(this.time_expire);
			sb.append("]]></time_expire>");
		}
		if (this.goods_tag != null) {
			sb.append("<goods_tag><![CDATA[");
			sb.append(this.goods_tag);
			sb.append("]]></goods_tag>");
		}
		if (this.notify_url != null) {
			sb.append("<notify_url><![CDATA[");
			sb.append(this.notify_url);
			sb.append("]]></notify_url>");
		}
		if (this.trade_type != null) {
			sb.append("<trade_type><![CDATA[");
			sb.append(this.trade_type);
			sb.append("]]></trade_type>");
		}
		if (this.product_id != null) {
			sb.append("<product_id><![CDATA[");
			sb.append(this.product_id);
			sb.append("]]></product_id>");
		}
		if (this.limit_pay != null) {
			sb.append("<limit_pay><![CDATA[");
			sb.append(this.limit_pay);
			sb.append("]]></limit_pay>");
		}
		if (this.openid != null) {
			sb.append("<openid><![CDATA[");
			sb.append(this.openid);
			sb.append("]]></openid>");
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
	private void sign(String signKey) {
		StringBuilder sb = new StringBuilder();
		if (this.appid != null) {
			sb.append("appid=");
			sb.append(this.appid);
			sb.append("&");
		}
		if (this.attach != null) {
			sb.append("attach=");
			sb.append(this.attach);
			sb.append("&");
		}
		if (this.body != null) {
			sb.append("body=");
			sb.append(this.body);
			sb.append("&");
		}
		if (this.detail != null) {
			sb.append("detail=");
			sb.append(this.detail);
			sb.append("&");
		}
		if (this.device_info != null) {
			sb.append("device_info=");
			sb.append(this.device_info);
			sb.append("&");
		}
		if (this.fee_type != null) {
			sb.append("fee_type=");
			sb.append(this.fee_type);
			sb.append("&");
		}
		if (this.goods_tag != null) {
			sb.append("goods_tag=");
			sb.append(this.goods_tag);
			sb.append("&");
		}
		if (this.limit_pay != null) {
			sb.append("limit_pay");
			sb.append(this.limit_pay);
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
		if (this.notify_url != null) {
			sb.append("notify_url=");
			sb.append(this.notify_url);
			sb.append("&");
		}
		if (this.openid != null) {
			sb.append("openid=");
			sb.append(this.openid);
			sb.append("&");
		}
		if (this.out_trade_no != null) {
			sb.append("out_trade_no=");
			sb.append(this.out_trade_no);
			sb.append("&");
		}
		if (this.product_id != null) {
			sb.append("product_id=");
			sb.append(this.product_id);
			sb.append("&");
		}
		if (this.spbill_create_ip != null) {
			sb.append("spbill_create_ip=");
			sb.append(this.spbill_create_ip);
			sb.append("&");
		}
		if (this.time_expire != null) {
			sb.append("time_expire=");
			sb.append(this.time_expire);
			sb.append("&");
		}
		if (this.time_start != null) {
			sb.append("time_start=");
			sb.append(this.time_start);
			sb.append("&");
		}
		if (this.total_fee != null) {
			sb.append("total_fee=");
			sb.append(this.total_fee);
			sb.append("&");
		}
		if (this.trade_type != null) {
			sb.append("trade_type=");
			sb.append(this.trade_type);
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
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

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
