package com.abapi.cloud.socket.mapping;

import com.abapi.cloud.socket.pojo.TcpSession;

/**
 * @Author ldx
 * @Date 2019/10/12 9:53
 * @Description
 * @Version 1.0.0
 */
public abstract class AbstractTcpEndpointHandler {

    /**终端连接**/
    public abstract void doOnOpen(TcpSession ctx);

    /**收到消息**/
    public abstract void doOnMessage(TcpSession ctx, Object msg);

    /**异常错误**/
    public abstract void doOnError(TcpSession ctx, Throwable cause);

    /**终端断开连接**/
    public abstract void doOnClose(TcpSession ctx);

    /**服务超时**/
    public abstract void doOnEvent(TcpSession ctx, Object evt);
}
