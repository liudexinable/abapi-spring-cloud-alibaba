package com.abapi.cloud.pay;

import com.abapi.cloud.pay.ali.AliPayBizConfig;
import com.abapi.cloud.pay.wx.WxPayBizConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ldx
 * @Date 2019/9/29 10:32
 * @Description
 * @Version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(PayConfigProperties.class)
@ConditionalOnProperty(name = "abapi.cloud.pay.config.enabled", matchIfMissing = true)
@ComponentScan(basePackages = {"com.abapi.cloud.pay.ali","com.abapi.cloud.pay.wx"})
public class PayConfigAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PayConfigProperties payConfigProperties;

    @Bean
    public AliPayBizConfig aliPayBizConfig() {
        AliPayBizConfig config = new AliPayBizConfig();
        BeanUtils.copyProperties(payConfigProperties,config);
        config.setOpen(payConfigProperties.getAliEnabled());
        logger.info("Load ALI Properties {}",config);
        return config;
    }

    @Bean
    public WxPayBizConfig wxPayBizConfig(){
        WxPayBizConfig config = new WxPayBizConfig();
        if(!payConfigProperties.getWxEnabled()){
           config.setOpen(payConfigProperties.getWxEnabled());
           return config;
        }

        if(payConfigProperties.getWxProperties().size() > 0){
            Map<String,WxPayBizConfig.WxPayConfig> map = new HashMap<>();
            payConfigProperties.getWxProperties().stream().forEach(e->{
                WxPayBizConfig.WxPayConfig wxPayConfig = new WxPayBizConfig.WxPayConfig();
                BeanUtils.copyProperties(e,wxPayConfig);
                map.put(e.getTradeType(),wxPayConfig);
            });
            config.setOpen(payConfigProperties.getWxEnabled());
            config.setWxPayConfigs(map);
        }
        logger.info("Load WX Properties {}",config);
        return config;
    }

}
