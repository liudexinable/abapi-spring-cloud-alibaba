package com.abapi.cloud.socket.service;

import cn.hutool.core.util.StrUtil;
import com.abapi.cloud.socket.NettyTcpConfigProperties;
import com.abapi.cloud.socket.handler.Decoder4LoggingOnly;
import com.abapi.cloud.socket.handler.TCPServerHandler;
import com.abapi.cloud.socket.mapping.TcpEndpointServerMapping;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Author ldx
 * @Date 2019/10/11 13:56
 * @Description
 * @Version 1.0.0
 */
public class NettyTcpService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NettyTcpConfigProperties nettyTcpConfigProperties;

    public NettyTcpService(NettyTcpConfigProperties nettyTcpConfigProperties){
        this.nettyTcpConfigProperties = nettyTcpConfigProperties;
    }

    public void start(){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)//绑定服务端通道NioServerSocketChannel
                .option(ChannelOption.SO_BACKLOG, nettyTcpConfigProperties.getSoBacklog())
                .childOption(ChannelOption.SO_KEEPALIVE, nettyTcpConfigProperties.getSoKeepalive())
                .childHandler(new ChannelInitializer<SocketChannel>() { //给读写事件的线程通道绑定handle去真正处理读写
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(nettyTcpConfigProperties.getReaderIdleTime(), nettyTcpConfigProperties.getWriterIdleTime(), nettyTcpConfigProperties.getAllIdleTime(), TimeUnit.SECONDS));
                        ch.pipeline().addLast(new Decoder4LoggingOnly());//添加解码handle
                        // 设置自定义handle编码/解码器
                        if(StrUtil.isNotEmpty(nettyTcpConfigProperties.getCustomMessageDecoder())){
                            Object o = Class.forName(nettyTcpConfigProperties.getCustomMessageDecoder()).newInstance();
                            ch.pipeline().addLast((ByteToMessageDecoder) o);
                        }
                        if(StrUtil.isNotEmpty(nettyTcpConfigProperties.getCustomMessageEncoder())){
                            Object o = Class.forName(nettyTcpConfigProperties.getCustomMessageEncoder()).newInstance();
                            ch.pipeline().addLast((MessageToByteEncoder) o);
                        }
                        // 1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
                        // 设置解码器
                        if(nettyTcpConfigProperties.getDecoder()!= null && nettyTcpConfigProperties.getDecoder().size() > 0){
                            List<ByteBuf> delimiters = new ArrayList<>();
                            nettyTcpConfigProperties.getDecoder().forEach(e->{
                                delimiters.add(Unpooled.copiedBuffer(e.getBytes()));
                            });
                            ByteBuf [] byteBufs = new ByteBuf[delimiters.size()];
                            delimiters.toArray(byteBufs);
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(nettyTcpConfigProperties.getMaxFrameLength(), byteBufs));
                        }else{
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(nettyTcpConfigProperties.getMaxFrameLength()));
                        }

                        ch.pipeline().addLast(new TCPServerHandler(new TcpEndpointServerMapping())); //ch.pipeline()管道中添加handle
                    }
                });

        try {
            bootstrap.bind(new InetSocketAddress(InetAddress.getByName(nettyTcpConfigProperties.getHost()), nettyTcpConfigProperties.getPort()));
        } catch (UnknownHostException e) {
            bootstrap.bind(new InetSocketAddress(nettyTcpConfigProperties.getHost(), nettyTcpConfigProperties.getPort()));
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            boss.shutdownGracefully().syncUninterruptibly();
            worker.shutdownGracefully().syncUninterruptibly();
        }));

        logger.info("------netty tcp host:"+nettyTcpConfigProperties.getHost()+" port:"+nettyTcpConfigProperties.getPort()+" started");
        //ChannelFuture channelFuture = bootstrap.bind(nettyTcpConfigProperties.getPort()).sync();

        //channelFuture.channel().closeFuture().sync();
    }

    public synchronized void startServer() {
        new Thread(() -> {
            try {
                logger.info("TCP服务启动中.......");
                this.start();
            } catch (Exception e) {
                logger.info("TCP服务启动出错:{}", e.getMessage());
                e.printStackTrace();
            }
        }, this.getName()).start();
    }

    private String getName() {
        return "TCP-Server";
    }

}
