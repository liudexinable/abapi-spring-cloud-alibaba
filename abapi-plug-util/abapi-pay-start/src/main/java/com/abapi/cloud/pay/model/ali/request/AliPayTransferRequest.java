package com.abapi.cloud.pay.model.ali.request;

import lombok.Data;

/**
 * @author liu
 * @date 2019/2/21 0021 16:33
 */
@Data
public class AliPayTransferRequest {

	/**转账编号**/
	private String outBizNo;

	/**转入账户 账号**/
	private String payeeAccount;

	/**转入金额**/
	private String amount;

	/**付款方姓名（最长支持100个英文/50个汉字）。显示在收款方的账单详情页。如果该字段不传，则默认显示付款方的支付宝认证姓名或单位名称。**/
	private String payerShowName;

	/**收款方真实姓名（最长支持100个英文/50个汉字）。
	 如果本参数不为空，则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。**/
	private String payeeRealName;

	/**转账备注**/
	private String remark;

}
