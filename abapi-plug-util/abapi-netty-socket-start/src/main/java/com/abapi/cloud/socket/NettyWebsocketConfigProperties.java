package com.abapi.cloud.socket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author ldx
 * @Date 2019/10/14 10:38
 * @Description
 * @Version 1.0.0
 */
@Data
@ConfigurationProperties(NettyWebsocketConfigProperties.PREFIX)
public class NettyWebsocketConfigProperties {

    public static final String PREFIX = "abapi.cloud.netty.websocket";

    private Boolean enabled = false;

    private String host = "0.0.0.0";

    private int port = 9989;

    private String path = "/";

    private int bossLoopGroupThreads = 0;

    private int workerLoopGroupThreads = 0;

    private Boolean useCompressionHandler = false;

    //------------------------- idleEvent -------------------------

    private int readerIdleTime = 0;

    private int writerIdleTime = 0;

    private int allIdleTime = 0;

    //------------------------- option -------------------------

    private int connectTimeoutMillis = 30000;

    private int soBacklog = 128;

    //------------------------- childOption -------------------------

    private int writeSpinCount = 16;

    private int writeBufferHighWaterMark = 64 * 1024;

    private int writeBufferLowWaterMark = 32 * 1024;

    private int soRcvbuf = -1;

    private int soSndbuf = -1;

    private boolean tcpNodelay = true;

    private boolean soKeepalive = false;

    private int soLinger = -1;

    private boolean allowHalfClosure = false;

    //------------------------- handshake -------------------------

    int maxFramePayloadLength = 65536;

}
