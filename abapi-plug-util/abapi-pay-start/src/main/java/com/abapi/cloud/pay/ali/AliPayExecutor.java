package com.abapi.cloud.pay.ali;

import com.abapi.cloud.pay.exception.PayException;
import com.abapi.cloud.pay.executor.AbstractPayExecutor;
import com.abapi.cloud.pay.model.ali.request.AliPayRefundRequest;
import com.abapi.cloud.pay.model.ali.request.AliPayRequest;
import com.abapi.cloud.pay.model.ali.request.AliPayTransferRequest;
import com.abapi.cloud.pay.model.ali.response.AliTradePayResponse;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ldx
 * @Date 2019/9/30 14:22
 * @Description
 * @Version 1.0.0
 */
@Component
public class AliPayExecutor extends AbstractPayExecutor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AliPayBizConfig aliConfig;

    @Autowired
    public void setAliPayBizConfig(){
        super.aliConfig = aliConfig;
    }

    @Override
    public void checkOpen() {
        if(!aliConfig.getOpen()){
            throw new PayException("未开启支付宝功能 [abapi.cloud.pay.ali-enabled=false]");
        }
    }

    @Override
    public void checkSourceProperties() {
        checkOpen();
        Assert.notNull(aliConfig.getAliAppId(),"abapi.cloud.pay.ali-app-id is null");
        Assert.notNull(aliConfig.getAliPrivateKey(),"abapi.cloud.pay.ali-public-key is null");
        Assert.notNull(aliConfig.getAliPublicKey(),"abapi.cloud.pay.ali-public-key is null");
        Assert.notNull(aliConfig.getAliPlatformPublicKey(),"abapi.cloud.pay.ali-platform-public-key is null");
        String signType = aliConfig.getAliSignType();
        if(signType.equals("RSA2")){
            Assert.notNull(aliConfig.getAliPublicKey256(),"abapi.cloud.pay.ali-public-key256 is null");
        }
    }

    public AliTradePayResponse toPay(AliPayRequest request,AliPayTrade trade){
        this.checkSourceProperties();
        if(trade == null){
            throw new IllegalArgumentException("trade can not be empty");
        }

        // 检查支付金额
        BigDecimal totalAmount = new BigDecimal(request.getTotalAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
        if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("totalAmount must be greater than 0.00");
        }

        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(request.getTotalAmount());
        if(match.matches()){
            request.setTotalAmount(totalAmount.toString());
        }

        if(trade.equals(AliPayTrade.APP)){
            request.setProductCode(AliBase.ALIPAY_TRADE_APP_PAY);
            return this.toPay2App(request);
        }else if(trade.equals(AliPayTrade.PAGE)){
            request.setProductCode(AliBase.ALIPAY_TRADE_PAGE_PAY);
            return this.toPay2Page(request);
        }else if(trade.equals(AliPayTrade.WAP)){
            request.setProductCode(AliBase.ALIPAY_TRADE_WAP_PAY);
            return this.toPay2Wap(request);
        }
        return null;
    }


    private AliTradePayResponse toPay2App(AliPayRequest aliPayRequest){
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        BeanUtils.copyProperties(aliPayRequest,model);
        request.setBizModel(model);
        request.setNotifyUrl(aliPayRequest.getCallback());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);
            AliTradePayResponse result = new AliTradePayResponse();
            if(response.isSuccess()){
                BeanUtils.copyProperties(response,result);
            }else{
                BeanUtils.copyProperties(response,result);
            }
            logger.debug("app支付>>>>"+response.getBody());
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new PayException("to app ali pay fail");
        }
    }

    public AliTradePayResponse toPay2Page(AliPayRequest aliPayRequest) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        request.setReturnUrl(aliPayRequest.getCallback());
        request.setNotifyUrl(aliPayRequest.getNotifyUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        BeanUtils.copyProperties(aliPayRequest,model);
        request.setBizModel(model);

        try {
            AlipayTradePagePayResponse response = getAlipayClient().pageExecute(request);
            AliTradePayResponse result = new AliTradePayResponse();
            if(response.isSuccess()){
                BeanUtils.copyProperties(response,result);
            }else{
                BeanUtils.copyProperties(response,result);
            }
            //调用SDK生成表单
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new PayException("to page ali pay fail");
        }
    }

    public AliTradePayResponse toPay2Wap(AliPayRequest aliPayRequest){
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(aliPayRequest.getCallback());
        request.setNotifyUrl(aliPayRequest.getNotifyUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        BeanUtils.copyProperties(aliPayRequest,model);
        request.setBizModel(model);

        try {
            AlipayTradeWapPayResponse response = getAlipayClient().pageExecute(request);
            AliTradePayResponse result = new AliTradePayResponse();
            if(response.isSuccess()){
                BeanUtils.copyProperties(response,result);
            }else{
                BeanUtils.copyProperties(response,result);
            }
            //调用SDK生成表单
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new PayException("to wap ali pay fail");
        }

    }

    /**
     * 退款
     * @param aliPayRefundRequest
     * @return
     */
    public Boolean toRefund(AliPayRefundRequest aliPayRefundRequest) {
        this.checkSourceProperties();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        Map<String, String> map = new HashMap<String, String>();
        map.put("out_trade_no", aliPayRefundRequest.getOutTradeNo());
        map.put("trade_no", aliPayRefundRequest.getTradeNo());
        map.put("refund_amount", aliPayRefundRequest.getRefundAmount());
        if(StringUtils.areNotEmpty(aliPayRefundRequest.getOutRequesrNo())){
            map.put("out_request_no", aliPayRefundRequest.getOutRequesrNo());
        }
        request.setBizContent(JSON.toJSONString(map));
        try {
            AlipayTradeRefundResponse response = getAlipayClientSpecial().execute(request);
            boolean result;
            if (response.isSuccess()) {
                result=true;
            }else {
                result = false;
                logger.error("退款失败:>>>"+JSON.toJSONString(response));
            }
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            logger.error("退款失败"+JSON.toJSONString(aliPayRefundRequest),e);
        }
        return false;
    }

    /**
     * 异步验证 并返回回调 参数
     * @param request
     * @return
     */
    public Map<String, String> asyCheckReturnParams(HttpServletRequest request) {
        this.checkSourceProperties();
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        logger.info("支付宝 回调参数>>>>"+JSON.toJSONString(params));
        try {
            if(AlipaySignature.rsaCheckV1(params, aliConfig.getAliPlatformPublicKey(), AliBase.CHARSET, aliConfig.getAliSignType())){
                return params;
            }else{
                throw new PayException("Fail check params bad");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new PayException("Fail check params bad");
        }
    }

    /**
     * 企业转账到支付宝账户
     * @param aliPayTransferRequest
     * @return
     */
    public Boolean toTransfer(AliPayTransferRequest aliPayTransferRequest){
        this.checkSourceProperties();
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("out_biz_no", aliPayTransferRequest.getOutBizNo());
        map.put("payee_type", "ALIPAY_LOGONID");
        map.put("payee_account", aliPayTransferRequest.getPayeeAccount());
        map.put("amount", aliPayTransferRequest.getAmount());
        map.put("payer_show_name", aliPayTransferRequest.getPayerShowName());
        map.put("payee_real_name", aliPayTransferRequest.getPayeeRealName());
        map.put("remark",aliPayTransferRequest.getRemark());
        request.setBizContent(JSON.toJSONString(map));
        AlipayFundTransToaccountTransferResponse response;
        try {
            response = getAlipayClientSpecial().execute(request);

            Boolean result = false;
            if(response.isSuccess()){
                logger.info("支付宝 转账成功 {}"+JSON.toJSONString(map));
                result = true;
            } else {
                logger.error("支付宝 转账失败 {}"+JSON.toJSONString(map));
            }
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            logger.error("转账失败",e);
            return false;
        }
    }

    /**
     * 查询转账结果
     * @param outBizNo
     * @return
     * @throws AlipayApiException
     */
    public String toQueryTransfer(String outBizNo) throws AlipayApiException {
        this.checkSourceProperties();
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        Map<String,String> map = new HashMap<String, String>(1);
        map.put("out_biz_no",outBizNo);
        request.setBizContent(JSON.toJSONString(map));
        AlipayFundTransOrderQueryResponse response = getAlipayClientSpecial().execute(request);
        if(response.isSuccess()){
            logger.info("查询 支付宝 转账成功 {}"+JSON.toJSONString(response));
        } else {
            logger.info("支付宝 转账失败 {}"+JSON.toJSONString(map));
        }
        return JSON.toJSONString(response);
    }

    /**
     * 查询交易结果
     * @param outTradeNo
     * @return
     */
    public AlipayTradeQueryResponse query(String outTradeNo){
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = getAlipayClient().execute(request);
            return response;
        }catch (Exception e){
            logger.error("查询交易失败",e);
            throw new PayException("query fail");
        }

    }
}
