package com.abapi.cloud.socket.mapping;

import com.abapi.cloud.socket.SpringContext;
import com.abapi.cloud.socket.annotation.TcpEndpointHandler;
import com.abapi.cloud.socket.pojo.TcpSession;
import com.abapi.cloud.socket.util.BindResultUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * @Author ldx
 * @Date 2019/10/11 16:54
 * @Description
 * @Version 1.0.0
 */
public class TcpEndpointServerMapping{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Class aClass;

    private static final AttributeKey POJO_KEY = AttributeKey.valueOf("TCP_IMPLEMENT");

    private static final AttributeKey<TcpSession> SESSION_KEY = AttributeKey.valueOf("TCP_SESSION");

    public TcpEndpointServerMapping(){
        ApplicationContext context = SpringContext.getApplicationContext();
        String[] endpointBeanNames = context.getBeanNamesForAnnotation(TcpEndpointHandler.class);
        String beanName = endpointBeanNames[0];
        aClass = context.getType(beanName);
    }

    public void doOnOpen(ChannelHandlerContext ctx) {
        try {
            Channel channel = ctx.channel();
            Attribute<TcpSession> attrSession = channel.attr(SESSION_KEY);
            TcpSession session = new TcpSession(channel);
            attrSession.set(session);

            Attribute attrPojo = channel.attr(POJO_KEY);
            final Object implement = SpringContext.getApplicationContext().getBean(aClass);
            attrPojo.set(implement);

            Method method = aClass.getDeclaredMethod("doOnOpen", BindResultUtil.bindParamClass(session));
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement,BindResultUtil.bindParamValue(session));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doOnMessage(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf buf = (ByteBuf) msg;
            if (buf.readableBytes() <= 0) {
                return;
            }

            Object implement = ctx.channel().attr(POJO_KEY).get();
            TcpSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("session is null");
                return;
            }
            if (implement == null){
                logger.error("implement is null");
                return;
            }
            Class [] classes = new Class[]{TcpSession.class,Object.class};
            Method method= aClass.getDeclaredMethod("doOnMessage",classes);
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement,BindResultUtil.bindParamValue(session,msg));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    public void doOnError(ChannelHandlerContext ctx, Throwable cause) {
        try {

            Object implement = ctx.channel().attr(POJO_KEY).get();
            TcpSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("session is null");
                return;
            }
            if (implement == null){
                logger.error("implement is null");
                return;
            }
            Class [] classes = new Class[]{TcpSession.class,Throwable.class};
            Method method= aClass.getDeclaredMethod("doOnError",classes);
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement,BindResultUtil.bindParamValue(session,cause));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOnClose(ChannelHandlerContext ctx) {
        try {

            Object implement = ctx.channel().attr(POJO_KEY).get();
            TcpSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("session is null");
                return;
            }
            if (implement == null){
                logger.error("implement is null");
                return;
            }

            Method method= aClass.getDeclaredMethod("doOnClose",BindResultUtil.bindParamClass(session));
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement,BindResultUtil.bindParamValue(session));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doOnEvent(ChannelHandlerContext ctx, Object evt) {
        try {

            Object implement = ctx.channel().attr(POJO_KEY).get();
            TcpSession session = ctx.channel().attr(SESSION_KEY).get();
            if (session == null ) {
                logger.error("session is null");
                return;
            }
            if (implement == null){
                logger.error("implement is null");
                return;
            }
            Class [] classes = new Class[]{TcpSession.class,Object.class};
            Method method= aClass.getDeclaredMethod("doOnEvent",classes);
            method.setAccessible(true);//设置为可调用私有方法
            method.invoke(implement,BindResultUtil.bindParamValue(session,evt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
