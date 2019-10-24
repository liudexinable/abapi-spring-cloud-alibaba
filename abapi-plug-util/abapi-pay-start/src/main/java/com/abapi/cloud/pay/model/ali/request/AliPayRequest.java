package com.abapi.cloud.pay.model.ali.request;

import lombok.Data;

/**
 * 支付宝 支付 参数模型
 * @author liu
 * @date 2019/2/20 0020 15:58
 */
@Data
public class AliPayRequest {

	/**商户网站唯一订单号**/
	private String outTradeNo;

	/**交易标题**/
	private String subject;

	/**支付金额**/
	private String totalAmount;

	/**异步回调地址**/
	private String callback;

	/**同步回调地址**/
	private String notifyUrl;

	/**
	 * 该笔订单允许的最晚付款时间，逾期将关闭交易。
	 * 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
	 * 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
	 */
	private String timeoutExpress;

	/**商品编码**/
	private String productCode;

	/**超时绝对时间 2018-01-01 12:00:00**/
	private String timeExpire;

	/**扩展参数**/
	private String passbackParams;

	/**用户退出 返回的地址**/
	private String quitUrl;

}
