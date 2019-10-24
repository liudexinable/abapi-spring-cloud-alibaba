package com.abapi.cloud.pay.wx;

import com.abapi.cloud.pay.wx.model.UnifiedOrderReturn;
import com.abapi.cloud.pay.wx.model.WxpayCloseReturn;
import com.abapi.cloud.pay.wx.model.WxpayQueryReturn;
import com.abapi.cloud.pay.wx.model.WxpayRefundReturn;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;


public final class WxpayXmlUtil {

	/**
	 * 
	 * 
	 * @return
	 */
	public static UnifiedOrderReturn parseUnifiedOrderReturn(String xml) {
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			if (doc == null) {
				return null;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		UnifiedOrderReturn unifiedOrderReturn = new UnifiedOrderReturn();

		Node node = doc.selectSingleNode("/xml/return_code");
		if (node != null) {
			unifiedOrderReturn.setReturn_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/return_msg");
		if (node != null) {
			unifiedOrderReturn.setReturn_msg(node.getText());
		}
		node = doc.selectSingleNode("/xml/appid");
		if (node != null) {
			unifiedOrderReturn.setAppid(node.getText());
		}
		node = doc.selectSingleNode("/xml/mch_id");
		if (node != null) {
			unifiedOrderReturn.setMch_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/device_info");
		if (node != null) {
			unifiedOrderReturn.setDevice_info(node.getText());
		}
		node = doc.selectSingleNode("/xml/nonce_str");
		if (node != null) {
			unifiedOrderReturn.setNonce_str(node.getText());
		}
		node = doc.selectSingleNode("/xml/sign");
		if (node != null) {
			unifiedOrderReturn.setSign(node.getText());
		}
		node = doc.selectSingleNode("/xml/result_code");
		if (node != null) {
			unifiedOrderReturn.setResult_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/err_code");
		if (node != null) {
			unifiedOrderReturn.setErr_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/err_code_des");
		if (node != null) {
			unifiedOrderReturn.setErr_code_des(node.getText());
		}
		node = doc.selectSingleNode("/xml/trade_type");
		if (node != null) {
			unifiedOrderReturn.setTrade_type(node.getText());
		}
		node = doc.selectSingleNode("/xml/prepay_id");
		if (node != null) {
			unifiedOrderReturn.setPrepay_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/code_url");
		if (node != null) {
			unifiedOrderReturn.setCode_url(node.getText());
		}
		return unifiedOrderReturn;
	}
	
	public static WxpayQueryReturn parseWxpayQueryReturn(String xml){
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			if (doc == null) {
				return null;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		WxpayQueryReturn wxpayQueryReturn = new WxpayQueryReturn();

		Node node = doc.selectSingleNode("/xml/return_code");
		if (node != null) {
			wxpayQueryReturn.setReturn_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/return_msg");
		if (node != null) {
			wxpayQueryReturn.setReturn_msg(node.getText());
		}
		node = doc.selectSingleNode("/xml/appid");
		if (node != null) {
			wxpayQueryReturn.setAppid(node.getText());
		}
		node = doc.selectSingleNode("/xml/mch_id");
		if (node != null) {
			wxpayQueryReturn.setMch_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/device_info");
		if (node != null) {
			wxpayQueryReturn.setDevice_info(node.getText());
		}
		node = doc.selectSingleNode("/xml/nonce_str");
		if (node != null) {
			wxpayQueryReturn.setNonce_str(node.getText());
		}
		node = doc.selectSingleNode("/xml/sign");
		if (node != null) {
			wxpayQueryReturn.setSign(node.getText());
		}
		node = doc.selectSingleNode("/xml/result_code");
		if (node != null) {
			wxpayQueryReturn.setResult_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/err_code");
		if (node != null) {
			wxpayQueryReturn.setErr_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/err_code_des");
		if (node != null) {
			wxpayQueryReturn.setErr_code_des(node.getText());
		}
		node = doc.selectSingleNode("/xml/openid");
		if (node != null) {
			wxpayQueryReturn.setOpenid(node.getText());
		}
		node = doc.selectSingleNode("/xml/is_subscribe");
		if (node != null) {
			wxpayQueryReturn.setIs_subscribe(node.getText());
		}
		node = doc.selectSingleNode("/xml/trade_type");
		if (node != null) {
			wxpayQueryReturn.setTrade_type(node.getText());
		}
		node = doc.selectSingleNode("/xml/trade_state");
		if (node != null) {
			wxpayQueryReturn.setTrade_state(node.getText());
		}
		node = doc.selectSingleNode("/xml/bank_type");
		if (node != null) {
			wxpayQueryReturn.setBank_type(node.getText());
		}
		node = doc.selectSingleNode("/xml/total_fee");
		if (node != null) {
			wxpayQueryReturn.setTotal_fee(node.getText());
		}
		node = doc.selectSingleNode("/xml/fee_type");
		if (node != null) {
			wxpayQueryReturn.setFee_type(node.getText());
		}
		node = doc.selectSingleNode("/xml/cash_fee");
		if (node != null) {
			wxpayQueryReturn.setCash_fee(node.getText());
		}
		node = doc.selectSingleNode("/xml/cash_fee_type");
		if (node != null) {
			wxpayQueryReturn.setCash_fee_type(node.getText());
		}
		node = doc.selectSingleNode("/xml/coupon_fee");
		if (node != null) {
			wxpayQueryReturn.setCoupon_fee(node.getText());
		}
		node = doc.selectSingleNode("/xml/coupon_count");
		if (node != null) {
			wxpayQueryReturn.setCoupon_count(node.getText());
		}
//		node = doc.selectSingleNode("/xml/coupon_batch_id_$n");
//		if (node != null) {
//			wxpayQueryReturn.setCoupon_batch_id_$n(node.getText());
//		}
//		node = doc.selectSingleNode("/xml/coupon_id_$n");
//		if (node != null) {
//			wxpayQueryReturn.setCoupon_id_$n(node.getText());
//		}
//		node = doc.selectSingleNode("/xml/coupon_fee_$n");
//		if (node != null) {
//			wxpayQueryReturn.setCoupon_fee_$n(node.getText());
//		}
		node = doc.selectSingleNode("/xml/transaction_id");
		if (node != null) {
			wxpayQueryReturn.setTransaction_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/out_trade_no");
		if (node != null) {
			wxpayQueryReturn.setOut_trade_no(node.getText());
		}
		node = doc.selectSingleNode("/xml/attach");
		if (node != null) {
			wxpayQueryReturn.setAttach(node.getText());
		}
		node = doc.selectSingleNode("/xml/time_end");
		if (node != null) {
			wxpayQueryReturn.setTime_end(node.getText());
		}
		node = doc.selectSingleNode("/xml/trade_state_desc");
		if (node != null) {
			wxpayQueryReturn.setTrade_state_desc(node.getText());
		}
		return wxpayQueryReturn;
	} 
	
	public static WxpayRefundReturn parseWxpayRefundReturn(String xml){
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			if (doc == null) {
				return null;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
		WxpayRefundReturn wxpayRefundReturn = new WxpayRefundReturn();
		
		Node node = doc.selectSingleNode("/xml/return_code");
		if (node != null) {
			wxpayRefundReturn.setReturn_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/return_msg");
		if (node != null) {
			wxpayRefundReturn.setReturn_msg(node.getText());
		}
		node = doc.selectSingleNode("/xml/result_code");
		if (node != null) {
			wxpayRefundReturn.setResult_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/appid");
		if (node != null) {
			wxpayRefundReturn.setAppid(node.getText());
		}
		node = doc.selectSingleNode("/xml/mch_id");
		if (node != null) {
			wxpayRefundReturn.setMch_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/nonce_str");
		if (node != null) {
			wxpayRefundReturn.setNonce_str(node.getText());
		}
		node = doc.selectSingleNode("/xml/sign");
		if (node != null) {
			wxpayRefundReturn.setSign(node.getText());
		}
		node = doc.selectSingleNode("/xml/transaction_id");
		if (node != null) {
			wxpayRefundReturn.setTransaction_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/out_trade_no");
		if (node != null) {
			wxpayRefundReturn.setOut_trade_no(node.getText());
		}
		node = doc.selectSingleNode("/xml/out_refund_no");
		if (node != null) {
			wxpayRefundReturn.setOut_refund_no(node.getText());
		}
		node = doc.selectSingleNode("/xml/refund_id");
		if (node != null) {
			wxpayRefundReturn.setRefund_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/refund_fee");
		if (node != null) {
			wxpayRefundReturn.setRefund_fee(node.getText());
		}
		return wxpayRefundReturn;
	}
	
	public static WxpayCloseReturn parseWxpayCloseReturn(String xml){
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			if (doc == null) {
				return null;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		WxpayCloseReturn wxpayColseReturn = new WxpayCloseReturn();

		Node node = doc.selectSingleNode("/xml/return_code");
		if (node != null) {
			wxpayColseReturn.setReturn_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/return_msg");
		if (node != null) {
			wxpayColseReturn.setReturn_msg(node.getText());
		}
		node = doc.selectSingleNode("/xml/appid");
		if (node != null) {
			wxpayColseReturn.setAppid(node.getText());
		}
		node = doc.selectSingleNode("/xml/mch_id");
		if (node != null) {
			wxpayColseReturn.setMch_id(node.getText());
		}
		node = doc.selectSingleNode("/xml/nonce_str");
		if (node != null) {
			wxpayColseReturn.setNonce_str(node.getText());
		}
		node = doc.selectSingleNode("/xml/sign");
		if (node != null) {
			wxpayColseReturn.setSign(node.getText());
		}
		node = doc.selectSingleNode("/xml/result_code");
		if (node != null) {
			wxpayColseReturn.setResult_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/err_code");
		if (node != null) {
			wxpayColseReturn.setErr_code(node.getText());
		}
		node = doc.selectSingleNode("/xml/err_code_des");
		if (node != null) {
			wxpayColseReturn.setErr_code_des(node.getText());
		}
		return wxpayColseReturn;
	} 
}
