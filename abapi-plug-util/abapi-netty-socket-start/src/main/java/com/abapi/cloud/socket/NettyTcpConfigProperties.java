package com.abapi.cloud.socket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * @Author ldx
 * @Date 2019/9/27 18:11
 * @Description
 * @Version 1.0.0
 */
@Data
@ConfigurationProperties(NettyTcpConfigProperties.PREFIX)
public class NettyTcpConfigProperties {

    public static final String PREFIX = "abapi.cloud.netty.tcp";

    private Boolean enabled = false;

    private String host = "0.0.0.0";

    private int port = 9979;

    private int readerIdleTime = 0;

    private int writerIdleTime = 0;

    private int allIdleTime = 0;

    private Integer soBacklog = 128;

    private Boolean soKeepalive = true;

    private int maxFrameLength = 1024;

    private List<String> decoder;

    private String customMessageDecoder;

    private String customMessageEncoder;

}
