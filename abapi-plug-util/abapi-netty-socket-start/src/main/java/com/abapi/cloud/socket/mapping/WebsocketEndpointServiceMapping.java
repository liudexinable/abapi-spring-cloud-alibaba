package com.abapi.cloud.socket.mapping;

import com.abapi.cloud.socket.SpringContext;
import com.abapi.cloud.socket.annotation.WebsocketEndpointHandler;
import com.abapi.cloud.socket.pojo.ParameterMap;
import com.abapi.cloud.socket.pojo.WebsocketSession;
import com.abapi.cloud.socket.util.BindResultUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * @Author ldx
 * @Date 2019/10/15 9:56
 * @Description
 * @Version 1.0.0
 */
public class WebsocketEndpointServiceMapping {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Class aClass;

    private static final AttributeKey POJO_KEY = AttributeKey.valueOf("WEBSOCKET_IMPLEMENT");

    private static final AttributeKey<WebsocketSession> SESSION_KEY = AttributeKey.valueOf("WEBSOCKET_SESSION");

    public WebsocketEndpointServiceMapping(){
        ApplicationContext context = SpringContext.getApplicationContext();
        String[] endpointBeanNames = context.getBeanNamesForAnnotation(WebsocketEndpointHandler.class);
        String beanName = endpointBeanNames[0];
        aClass = context.getType(beanName);
    }

    public void doOnOpen(ChannelHandlerContext ctx, FullHttpRequest req, String path, String originalParam) {
        try {
            Channel channel = ctx.channel();
            Attribute<WebsocketSession> attrSession = channel.attr(SESSION_KEY);
            WebsocketSession session = new WebsocketSession(channel);
            attrSession.set(session);

            HttpHeaders httpHeaders = req.headers();
            ParameterMap parameterMap = new ParameterMap(originalParam);
            Class [] classes = new Class[]{WebsocketSession.class,HttpHeaders.class,ParameterMap.class};
            Method method = aClass.getDeclaredMethod("doOnOpen", classes);

            Attribute attrPojo = channel.attr(POJO_KEY);
            Object implement = aClass.newInstance();
            attrPojo.set(implement);

            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement, BindResultUtil.bindParamValue(session,httpHeaders,parameterMap));
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doOnClose(ChannelHandlerContext ctx) {
        try {
            Object implement = ctx.channel().attr(POJO_KEY).get();
            WebsocketSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("websocket doOnClose session is null");
                return;
            }
            if (implement == null){
                logger.error("websocket doOnClose implement is null");
                return;
            }
            Method method = aClass.getDeclaredMethod("doOnClose", BindResultUtil.bindParamClass(session));
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement, BindResultUtil.bindParamValue(session));
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doOnError(ChannelHandlerContext ctx, Throwable throwable) {
        try {
            Object implement = ctx.channel().attr(POJO_KEY).get();
            WebsocketSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("websocket doOnClose session is null");
                return;
            }
            if (implement == null){
                logger.error("websocket doOnClose implement is null");
                return;
            }
            Class [] classes = new Class[]{WebsocketSession.class,Throwable.class};
            Method method = aClass.getDeclaredMethod("doOnError", classes);
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement, BindResultUtil.bindParamValue(session,throwable));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOnMessage(ChannelHandlerContext ctx, WebSocketFrame frame) {
        try {
            Object implement = ctx.channel().attr(POJO_KEY).get();
            WebsocketSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("websocket doOnMessage session is null");
                return;
            }
            if (implement == null){
                logger.error("websocket doOnMessage implement is null");
                return;
            }
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            Method method = aClass.getDeclaredMethod("doOnMessage", BindResultUtil.bindParamClass(session,textFrame.text()));
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement, BindResultUtil.bindParamValue(session,textFrame.text()));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOnBinary(ChannelHandlerContext ctx, WebSocketFrame frame) {
        try {
            Object implement = ctx.channel().attr(POJO_KEY).get();
            WebsocketSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("websocket doOnBinary session is null");
                return;
            }
            if (implement == null){
                logger.error("websocket doOnBinary implement is null");
                return;
            }
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf content = binaryWebSocketFrame.content();
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);

            Method method = aClass.getDeclaredMethod("doOnBinary", BindResultUtil.bindParamClass(session,bytes));
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement, BindResultUtil.bindParamValue(session,bytes));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOnEvent(ChannelHandlerContext ctx, Object evt) {
        try {
            Object implement = ctx.channel().attr(POJO_KEY).get();
            WebsocketSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("websocket doOnEvent session is null");
                return;
            }
            if (implement == null){
                logger.error("websocket doOnEvent implement is null");
                return;
            }
            Class [] classes = new Class[]{WebsocketSession.class,Object.class};
            Method method = aClass.getDeclaredMethod("doOnEvent", classes);
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement, BindResultUtil.bindParamValue(session,evt));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
