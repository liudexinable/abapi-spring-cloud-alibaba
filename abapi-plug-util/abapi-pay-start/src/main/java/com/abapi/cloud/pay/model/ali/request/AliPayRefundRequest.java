package com.abapi.cloud.pay.model.ali.request;

import lombok.Data;

/**
 * @author liu
 * @date 2019/2/21 0021 16:33
 */
@Data
public class AliPayRefundRequest {

	/**商户网站唯一订单号**/
	private String outTradeNo;

	/**支付宝 交易流水号**/
	private String tradeNo;

	/**退款金额**/
	private String refundAmount;

	/**退款编号**/
	private String outRequesrNo;

}
