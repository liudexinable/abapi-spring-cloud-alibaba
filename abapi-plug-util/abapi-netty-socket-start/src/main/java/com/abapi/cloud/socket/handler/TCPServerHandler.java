package com.abapi.cloud.socket.handler;

import com.abapi.cloud.socket.mapping.TcpEndpointServerMapping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ldx
 * @Date 2019/10/11 16:35
 * @Description
 * @Version 1.0.0
 */
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TcpEndpointServerMapping tcpEndpointServerMapping;

    public TCPServerHandler(TcpEndpointServerMapping tcpEndpointServerMapping){
        this.tcpEndpointServerMapping = tcpEndpointServerMapping;
    }

    /**
     * 读取客户端通道的数据
     * @param ctx
     * @param msg
     * @throws InterruptedException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException { // (2)
        logger.info("收到消息:{}", msg);
        tcpEndpointServerMapping.doOnMessage(ctx,msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        logger.info("发生异常:{}", cause.getMessage());
        tcpEndpointServerMapping.doOnError(ctx,cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("终端连接:{}", ctx.channel().id());
        tcpEndpointServerMapping.doOnOpen(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final String sessionId = ctx.channel().id().asLongText();
        logger.info("终端断开连接:{}", sessionId);
        tcpEndpointServerMapping.doOnClose(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        tcpEndpointServerMapping.doOnEvent(ctx, evt);
        /*if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.info("服务器主动断开连接:{}", ctx.channel().id());
                ctx.close();
            }
        }*/
    }

}
