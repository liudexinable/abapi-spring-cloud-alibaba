package com.abapi.cloud.socket.service;

import com.abapi.cloud.socket.NettyWebsocketConfigProperties;
import com.abapi.cloud.socket.handler.HttpServerHandler;
import com.abapi.cloud.socket.mapping.WebsocketEndpointServiceMapping;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @Author ldx
 * @Date 2019/10/14 10:33
 * @Description
 * @Version 1.0.0
 */
public class NettyWebsocketService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NettyWebsocketConfigProperties nettyWebsocketConfigProperties;

    public NettyWebsocketService(NettyWebsocketConfigProperties nettyWebsocketConfigProperties){
        this.nettyWebsocketConfigProperties = nettyWebsocketConfigProperties;
    }

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup(nettyWebsocketConfigProperties.getBossLoopGroupThreads());
        EventLoopGroup worker = new NioEventLoopGroup(nettyWebsocketConfigProperties.getWorkerLoopGroupThreads());
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettyWebsocketConfigProperties.getConnectTimeoutMillis())
                .option(ChannelOption.SO_BACKLOG, nettyWebsocketConfigProperties.getSoBacklog())
                .childOption(ChannelOption.WRITE_SPIN_COUNT, nettyWebsocketConfigProperties.getWriteSpinCount())
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(nettyWebsocketConfigProperties.getWriteBufferLowWaterMark(), nettyWebsocketConfigProperties.getWriteBufferHighWaterMark()))
                .childOption(ChannelOption.TCP_NODELAY, nettyWebsocketConfigProperties.isTcpNodelay())
                .childOption(ChannelOption.SO_KEEPALIVE, nettyWebsocketConfigProperties.isSoKeepalive())
                .childOption(ChannelOption.SO_LINGER, nettyWebsocketConfigProperties.getSoLinger())
                .childOption(ChannelOption.ALLOW_HALF_CLOSURE, nettyWebsocketConfigProperties.isAllowHalfClosure())
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(65536));
                        pipeline.addLast(new HttpServerHandler(nettyWebsocketConfigProperties,new WebsocketEndpointServiceMapping()));
                    }
                });

        if (nettyWebsocketConfigProperties.getSoRcvbuf() != -1) {
            bootstrap.childOption(ChannelOption.SO_RCVBUF, nettyWebsocketConfigProperties.getSoRcvbuf());
        }

        if (nettyWebsocketConfigProperties.getSoSndbuf() != -1) {
            bootstrap.childOption(ChannelOption.SO_SNDBUF, nettyWebsocketConfigProperties.getSoSndbuf());
        }

        ChannelFuture channelFuture;
        if ("0.0.0.0".equals(nettyWebsocketConfigProperties.getHost())) {
            channelFuture = bootstrap.bind(nettyWebsocketConfigProperties.getPort());
        } else {
            try {
                channelFuture = bootstrap.bind(new InetSocketAddress(InetAddress.getByName(nettyWebsocketConfigProperties.getHost()), nettyWebsocketConfigProperties.getPort()));
            } catch (UnknownHostException e) {
                channelFuture = bootstrap.bind(nettyWebsocketConfigProperties.getHost(), nettyWebsocketConfigProperties.getPort());
                e.printStackTrace();
            }
        }

        channelFuture.addListener(future -> {
            if (!future.isSuccess()){
                future.cause().printStackTrace();
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            boss.shutdownGracefully().syncUninterruptibly();
            worker.shutdownGracefully().syncUninterruptibly();
        }));

        logger.info("------netty websocket host:"+nettyWebsocketConfigProperties.getHost()+" port:"+nettyWebsocketConfigProperties.getPort()+" started");
    }

}
