package com.abapi.cloud.pay.wx;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.ssl.SSLSocketFactoryBuilder;
import com.abapi.cloud.pay.exception.PayException;
import com.abapi.cloud.pay.executor.AbstractPayExecutor;
import com.abapi.cloud.pay.model.wx.request.WxPayRequest;
import com.abapi.cloud.pay.model.wx.request.WxRefundRequest;
import com.abapi.cloud.pay.model.wx.response.WxPayResponse;
import com.abapi.cloud.pay.wx.model.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ldx
 * @Date 2019/10/10 10:37
 * @Description
 * @Version 1.0.0
 */
@Component
public class WxPayExecutor extends AbstractPayExecutor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WxPayBizConfig wxPayBizConfig;

    @Override
    public void checkOpen() {
        if(!wxPayBizConfig.getOpen()){
            throw new PayException("未开启微信功能 [abapi.cloud.pay.wx-enabled=false]");
        }
    }

    @Override
    public void checkSourceProperties() {
        this.checkOpen();
        wxPayBizConfig.getWxPayConfigs().forEach((k,v)->{
            logger.info("pay check {} params {}",k,v);
            Assert.notNull(v.getTradeType(),"abapi.cloud.pay.wx-properties[N].trade-type is null");
            Assert.notNull(v.getWxIp(),"abapi.cloud.pay.wx-properties[N].wx-ip is null");
            Assert.notNull(v.getWxAppId(),"abapi.cloud.pay.wx-properties[N].wx-app-id is null");
            Assert.notNull(v.getWxMchId(),"abapi.cloud.pay.wx-properties[N].wx-mch-id is null");
            Assert.notNull(v.getWxSecret(),"abapi.cloud.pay.wx-properties[N].wx-secret is null");
            Assert.notNull(v.getWxCertPath12(),"abapi.cloud.pay.wx-properties[N].wx-cert-path12 is null");
        });
    }

    public WxPayResponse toPay(WxPayRequest request,WxPayTrade trade){
        checkSourceProperties();

        WxPayResponse response = new WxPayResponse();

        WxPayBizConfig.WxPayConfig wxPayConfig = wxPayBizConfig.getWxPayConfigs().get(trade.toString());
        UnifiedOrder order = bulidUnifiedOrder(request,wxPayConfig);
        String url = WxBase.UNIFIED_ORDER_URL;
        if(wxPayConfig.getWxSandbox()){
            // 获取沙箱 signKey
            String nonceStr = RandomUtil.randomString(10);
            String key = "mch_id="+wxPayConfig.getWxMchId()+"&nonce_str="+nonceStr+"&key="+wxPayConfig.getWxSecret();
            String xmlParam = "<xml><mch_id>"+wxPayConfig.getWxMchId()+"</mch_id><nonce_str>"+nonceStr+"</nonce_str><sign>"+MD5.create().digestHex(key)+"</sign></xml>";
            String r = HttpUtil.post(WxBase.SIGN_KEY_SANDBOX_URL, xmlParam);
            logger.info("沙箱环境getSignKey:"+r);
            Map<String, String> signKeyMap = XmlHelper.of(r).toMap();
            wxPayConfig.setWxSecret(signKeyMap.get("sandbox_signkey"));
            if(StringUtils.isEmpty(wxPayConfig.getWxSecret())){
                Assert.notNull(wxPayConfig.getWxSecret(),"微信pay 沙箱环境获取signkey失败");
            }
            url = WxBase.UNIFIED_ORDER_SANDBOX_URL;
        }
        String result = HttpUtil.post(url, order.genXml(wxPayConfig.getWxSecret()));
        logger.info("微信预下单返回:"+result);
        UnifiedOrderReturn unifiedOrderReturn = WxpayXmlUtil
                .parseUnifiedOrderReturn(result);

        if (unifiedOrderReturn.signVerify(wxPayConfig.getWxSecret())
                && WxBase.SUCCESS.equals(unifiedOrderReturn.getReturn_code())
                &&  WxBase.SUCCESS.equals(unifiedOrderReturn.getResult_code())
                && wxPayConfig.getWxAppId().equals(unifiedOrderReturn.getAppid())
                && wxPayConfig.getWxMchId().equals(unifiedOrderReturn.getMch_id()))
        {
            // 二次签名
            WxpayReqModel wxpayReqModel = new WxpayReqModel();
            wxpayReqModel.setAppid(unifiedOrderReturn.getAppid());
            wxpayReqModel.setNoncestr(RandomUtil.randomString(6));
            wxpayReqModel.setPackageValue("Sign=WXPay");
            wxpayReqModel.setPartnerid(wxPayConfig.getWxMchId());
            wxpayReqModel.setPrepayid(unifiedOrderReturn.getPrepay_id());
            wxpayReqModel.setTimestamp(String.valueOf(System
                    .currentTimeMillis() / 1000));
            wxpayReqModel.sign(wxPayConfig.getWxSecret());


            response.setAppId(wxPayConfig.getWxAppId());
            response.setOutTradeNo(request.getOutTradeNo());
            response.setTotalAmount(request.getTotalFee());
            response.setPrepayId(unifiedOrderReturn.getPrepay_id());
            response.setNonceStr(wxpayReqModel.getNoncestr());
            response.setSign(wxpayReqModel.getSign());
            response.setTimeStamp(wxpayReqModel.getTimestamp());
            response.setMchId(wxPayConfig.getWxMchId());
            response.setCode(unifiedOrderReturn.getReturn_code());
            if(trade.equals(WxPayTrade.NATIVE)){
                response.setQRStr(unifiedOrderReturn.getCode_url());
            }else if(trade.equals(WxPayTrade.JSAPI)){
                String sign = "appId="+wxPayConfig.getWxAppId()+"&nonceStr="+response.getNonceStr()+"&package=prepay_id="+response.getPrepayId()+"&signType=MD5&timeStamp="+response.getTimeStamp()+"&key="+wxPayConfig.getWxMchId();
                String paySign = MD5.create().digestHex(sign.getBytes()).toUpperCase();
                response.setPaySign(paySign);
            }
            response.setUnifiedOrderReturn(unifiedOrderReturn);
        }else{
            response.setCode(unifiedOrderReturn.getReturn_code());
            response.setUnifiedOrderReturn(unifiedOrderReturn);
            logger.error(response.getCode()+">"+response.getErrorMsg());
        }
        return response;
    }


    private UnifiedOrder bulidUnifiedOrder(WxPayRequest request, WxPayBizConfig.WxPayConfig wxPayConfig){
        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setBody(request.getBody());
        unifiedOrder.setOut_trade_no(request.getOutTradeNo());
        unifiedOrder.setTotal_fee(request.getTotalFee());
        unifiedOrder.setAppid(wxPayConfig.getWxAppId());
        unifiedOrder.setMch_id(wxPayConfig.getWxMchId());
        unifiedOrder.setSpbill_create_ip(wxPayConfig.getWxIp());
        unifiedOrder.setTrade_type(wxPayConfig.getTradeType());
        unifiedOrder.setNonce_str(RandomUtil.randomString(10));
        unifiedOrder.setNotify_url(request.getCallBack());
        unifiedOrder.setAttach(request.getAttach());
        unifiedOrder.setTime_expire(request.getTimeExpire());
        return unifiedOrder;
    }

    public String toRefund(WxRefundRequest request,WxPayTrade trade){
        Assert.notNull(request.getOutTradeNo(),"wx refund check miss OutTradeNo");
        Assert.notNull(request.getTradeNo(),"wx refund check miss TradeNo");
        Assert.notNull(request.getTotalFee(),"wx refund check miss TotalFee");
        Assert.notNull(request.getReturnFee(),"wx refund check miss ReturnFee");
        Assert.notNull(request.getOutRequesrNo(),"wx refund check miss OutRequesrNo");

        WxPayBizConfig.WxPayConfig wxPayConfig = wxPayBizConfig.getWxPayConfigs().get(trade.toString());

        WxpayRefund wxpayRefund = new WxpayRefund();
        wxpayRefund.setAppid(wxPayConfig.getWxAppId());
        wxpayRefund.setMch_id(wxPayConfig.getWxMchId());
        wxpayRefund.setNonce_str(RandomUtil.randomString(10));
        wxpayRefund.setTransaction_id(request.getTradeNo());
        wxpayRefund.setOut_trade_no(request.getOutTradeNo());
        wxpayRefund.setTotal_fee(request.getTotalFee());
        wxpayRefund.setRefund_fee(request.getReturnFee());
        wxpayRefund.setOut_refund_no(request.getOutRequesrNo());

        if(wxPayConfig.getWxSandbox()){
            // 获取沙箱 signKey
            String nonceStr = RandomUtil.randomString(10);
            String key = "mch_id="+wxPayConfig.getWxMchId()+"&nonce_str="+nonceStr+"&key="+wxPayConfig.getWxSecret();
            String xmlParam = "<xml><mch_id>"+wxPayConfig.getWxMchId()+"</mch_id><nonce_str>"+nonceStr+"</nonce_str><sign>"+MD5.create().digestHex(key)+"</sign></xml>";
            String r = HttpUtil.post(WxBase.SIGN_KEY_SANDBOX_URL, xmlParam);
            logger.info("沙箱环境getSignKey:"+r);
            Map<String, String> signKeyMap = XmlHelper.of(r).toMap();
            wxPayConfig.setWxSecret(signKeyMap.get("sandbox_signkey"));
            if(StringUtils.isEmpty(wxPayConfig.getWxSecret())){
                Assert.notNull(wxPayConfig.getWxSecret(),"微信pay 沙箱环境获取signkey失败");
            }
        }

        logger.info(wxpayRefund.genXml(wxPayConfig.getWxSecret()));
        WxpayRefundReturn wxpayRefundReturn = null;
        try {

            String result = HttpRequest.post(wxPayConfig.getWxSandbox() ? WxBase.REFUND_ORDER_SANDBOX_URL : WxBase.REFUND_ORDER_URL)
                    .setSSLSocketFactory(SSLSocketFactoryBuilder
                            .create()
                            .setProtocol(SSLSocketFactoryBuilder.TLSv1)
                            .setKeyManagers(getKeyManager(wxPayConfig.getWxMchId(), wxPayConfig.getWxCertPath12()))
                            .setSecureRandom(new SecureRandom())
                            .build()
                    )
                    .body(wxpayRefund.genXml(wxPayConfig.getWxSecret()))
                    .execute()
                    .body();
            logger.info(result);
            wxpayRefundReturn = WxpayXmlUtil.parseWxpayRefundReturn(result);
            if (WxBase.SUCCESS.equals(wxpayRefundReturn.getReturn_code())
                    && WxBase.SUCCESS.equals(wxpayRefundReturn.getResult_code())
                    && wxPayConfig.getWxAppId().equals(wxpayRefundReturn.getAppid())
                    && wxPayConfig.getWxMchId().equals(wxpayRefundReturn.getMch_id())) {
                return wxpayRefundReturn.getReturn_code();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WxBase.FAIL;

    }

    private KeyManager[] getKeyManager(String certPass, String certPath) throws Exception {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(new FileInputStream(certPath), certPass.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, certPass.toCharArray());
        return kmf.getKeyManagers();
    }


    public WxpayQueryReturn orderQuery(String outTradeNo,WxPayTrade trade){
        WxPayBizConfig.WxPayConfig wxPayConfig = wxPayBizConfig.getWxPayConfigs().get(trade.toString());

        WxpayQuery query = new WxpayQuery();
        query.setAppid(wxPayConfig.getWxAppId());
        query.setMch_id(wxPayConfig.getWxMchId());
        query.setOut_trade_no(outTradeNo);
        query.setNonce_str(RandomUtil.randomString(10));
        query.genXml(wxPayConfig.getWxSecret());

        String retXml = HttpUtil.post(wxPayConfig.getWxSandbox() ? WxBase.QUERY_ORDER_SANDBOX_URL : WxBase.QUERY_ORDER_URL,
                query.genXml(wxPayConfig.getWxSecret()));
        WxpayQueryReturn wxpayQueryReturn = WxpayXmlUtil.parseWxpayQueryReturn(retXml);
        return wxpayQueryReturn;

    }

    public Map<String,String> asyCheckReturnParams(HttpServletRequest request){
        Document doc = null;
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String resultStr  = new String(outSteam.toByteArray(),"utf-8");

            StringReader read = new StringReader(resultStr);
            InputSource inputSource = new InputSource(read);
            SAXReader sb = new SAXReader();
            doc = sb.read(inputSource);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, String> map = XMLUtil.Dom2Map(doc);
        return map;
    }

    public static void main(String[] args) {
        // 获取沙箱key
        Map<String,Object> map = new HashMap<>();
        map.put("mch_id","1519171091");
        map.put("nonce_str","12365");
        String a = "mch_id=1519171091&nonce_str=12365&key=e1782d270825de8820d763af006Sviwo";
        map.put("sign",MD5.create().digestHex(a));

        String b = "<xml><mch_id>1519171091</mch_id><nonce_str>12365</nonce_str><sign>"+MD5.create().digestHex(a)+"</sign></xml>";

        String post = HttpUtil.post(WxBase.SIGN_KEY_SANDBOX_URL, b);
        System.out.println(post);
        Map<String, String> sandbox_signkey = XmlHelper.of(post).toMap();
        System.out.println(sandbox_signkey.toString());
        //8dcfdc32b1592ad895eb01fcf64f3acf



        WxPayRequest request1 = new WxPayRequest();
        request1.setTotalFee("101");
        request1.setBody("测试123");
        request1.setCallBack("http://127.0.0.1");
        request1.setOutTradeNo(RandomUtil.randomString(16));
        WxPayBizConfig.WxPayConfig wxPayConfig = new WxPayBizConfig.WxPayConfig();
        wxPayConfig.setTradeType("APP");
        wxPayConfig.setWxAppId("wxe424468bae2177a6");
        wxPayConfig.setWxIp("127.0.0.1");
        wxPayConfig.setWxSecret("fbb3930dd6fa252ca1ecfe6f5f514ad0");
        wxPayConfig.setWxMchId("1519171091");
        UnifiedOrder order = new WxPayExecutor().bulidUnifiedOrder(request1,wxPayConfig);
        String url = WxBase.UNIFIED_ORDER_SANDBOX_URL;
        String result = HttpUtil.post(url, order.genXml(wxPayConfig.getWxSecret()));
        System.out.println(result);
    }

    public WxpayCloseReturn orderClose(String payNo, WxPayTrade trade){
        WxPayBizConfig.WxPayConfig wxPayConfig = wxPayBizConfig.getWxPayConfigs().get(trade.toString());
        WxpayClose wxpayClose = new WxpayClose();
        wxpayClose.setAppid(wxPayConfig.getWxAppId());
        wxpayClose.setMch_id(wxPayConfig.getWxMchId());
        wxpayClose.setOut_trade_no(payNo);
        wxpayClose.setNonce_str(RandomUtil.randomString(8));
        wxpayClose.genXml(wxPayConfig.getWxSecret());

        String result = HttpUtil.post(WxBase.CLOSE_ORDER_URl,
                wxpayClose.genXml(wxPayConfig.getWxSecret()));

        WxpayCloseReturn wxpayCloseReturn = WxpayXmlUtil.parseWxpayCloseReturn(result);
        return wxpayCloseReturn;
    }
}
