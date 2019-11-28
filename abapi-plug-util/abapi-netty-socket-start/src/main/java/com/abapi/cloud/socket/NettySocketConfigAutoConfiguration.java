package com.abapi.cloud.socket;

import com.abapi.cloud.socket.service.NettyTcpService;
import com.abapi.cloud.socket.service.NettyWebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ldx
 * @Date 2019/9/29 10:32
 * @Description
 * @Version 1.0.0
 */
@Configuration
@EnableConfigurationProperties({NettyTcpConfigProperties.class,NettyWebsocketConfigProperties.class})
@ConditionalOnProperty(name = "abapi.cloud.netty.socket.config.enabled", matchIfMissing = true)
public class NettySocketConfigAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    NettyTcpConfigProperties nettyTcpConfigProperties;

    @Autowired
    NettyWebsocketConfigProperties nettyWebsocketConfigProperties;

    @Bean
    public NettyTcpService startTcpService(){
        if(!nettyTcpConfigProperties.getEnabled()){
            return null;
        }
        logger.info("tcp start>{}",nettyTcpConfigProperties);
        NettyTcpService nettyTcpService = new NettyTcpService(nettyTcpConfigProperties);
        nettyTcpService.start();
        //nettyTcpService.startServer();
        return nettyTcpService;
    }

    @Bean
    public NettyWebsocketService startWebsocketService(){
        if(!nettyWebsocketConfigProperties.getEnabled()){
            return null;
        }
        logger.info("websocket start>{}",nettyWebsocketConfigProperties);
        NettyWebsocketService nettyWevbsocketService = new NettyWebsocketService(nettyWebsocketConfigProperties);
        nettyWevbsocketService.start();
        return nettyWevbsocketService;
    }

}
