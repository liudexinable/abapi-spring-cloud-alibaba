package com.abapi.cloud.socket.handler;

import com.abapi.cloud.socket.mapping.WebsocketEndpointServiceMapping;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

/**
 * @Author ldx
 * @Date 2019/10/14 11:54
 * @Description
 * @Version 1.0.0
 */
public class WebsccoketServiceHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final WebsocketEndpointServiceMapping websocketEndpointServiceMapping;

    public WebsccoketServiceHandler(WebsocketEndpointServiceMapping websocketEndpointServiceMapping){
        this.websocketEndpointServiceMapping = websocketEndpointServiceMapping;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        handleWebSocketFrame(ctx,webSocketFrame);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        websocketEndpointServiceMapping.doOnError(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        websocketEndpointServiceMapping.doOnClose(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        websocketEndpointServiceMapping.doOnEvent(ctx, evt);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            websocketEndpointServiceMapping.doOnMessage(ctx, frame);
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (frame instanceof CloseWebSocketFrame) {
            ctx.writeAndFlush(frame.retainedDuplicate()).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        if (frame instanceof BinaryWebSocketFrame) {
            websocketEndpointServiceMapping.doOnBinary(ctx, frame);
            return;
        }
        if (frame instanceof PongWebSocketFrame) {
            return;
        }
    }

}
