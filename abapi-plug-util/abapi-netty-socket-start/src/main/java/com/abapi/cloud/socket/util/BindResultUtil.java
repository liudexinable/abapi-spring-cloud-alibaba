package com.abapi.cloud.socket.util;

import java.util.Arrays;
import java.util.List;

/**
 * @Author ldx
 * @Date 2019/10/15 11:41
 * @Description
 * @Version 1.0.0
 */
public class BindResultUtil {

    public static Class[] bindParamClass(Object ... objects){
        Class [] classes = new Class[objects.length];
        List<Object> ol = Arrays.asList(objects);
        ol.forEach(e-> classes[ol.indexOf(e)] = e.getClass());
        return classes;
    }

    public static Object[] bindParamValue(Object ... objects){
        return objects;
    }

}
