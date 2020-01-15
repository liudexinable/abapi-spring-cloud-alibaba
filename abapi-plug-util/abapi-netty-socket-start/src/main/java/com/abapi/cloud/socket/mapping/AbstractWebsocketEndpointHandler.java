package com.abapi.cloud.socket.mapping;

import com.abapi.cloud.socket.pojo.ParameterMap;
import com.abapi.cloud.socket.pojo.WebsocketSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author ldx
 * @Date 2019/10/12 9:53
 * @Description
 * @Version 1.0.0
 */
public abstract class AbstractWebsocketEndpointHandler {

    /**终端连接成功**/
    public abstract void doOnOpen(WebsocketSession session,HttpHeaders httpHeaders, ParameterMap parameterMap);

    /**终端关闭**/
    public abstract void doOnClose(WebsocketSession session);

    /**异常错误**/
    public abstract void doOnError(WebsocketSession session, Throwable cause);

    /**收到消息**/
    public abstract void doOnMessage(WebsocketSession session,String message);

    /**收到二进制消息**/
    public abstract void doOnBinary(WebsocketSession session, byte[] bytes);

    /**服务超时**/
    public abstract void doOnEvent(WebsocketSession session, Object evt);
}
