package com.abapi.cloud.common.crypto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;

/**
 * @Author ldx
 * @Date 2019/9/24 13:06
 * @Description
 * @Version 1.0.0
 */
public class DES {

    public static String KEY;

    public static String IV;

    public static String MODE;

    public static String PADDING;

    private static Integer MIN_KEY_LEN = 8;

    private static volatile cn.hutool.crypto.symmetric.DES DO_DES;

    private static cn.hutool.crypto.symmetric.DES doDes() {
        if (DO_DES == null) {
            synchronized (DES.class) {
                cn.hutool.crypto.symmetric.DES des;
                if (StrUtil.isNotEmpty(KEY) &&
                        StrUtil.isNotEmpty(MODE) && StrUtil.isNotEmpty(PADDING)) {

                    if (KEY.length() < MIN_KEY_LEN ) {
                        throw new IllegalArgumentException("key nnly support length 8");
                    }

                    Mode mode = Mode.valueOf(MODE);

                    Padding padding = Padding.valueOf(PADDING);

                    des = new cn.hutool.crypto.symmetric.DES(mode, padding, KEY.getBytes());
                    if (StrUtil.isNotEmpty(IV)) {
                        des.setIv(IV.getBytes());
                    }
                } else if (StrUtil.isNotEmpty(KEY) &&
                        (StrUtil.isEmpty(MODE) || StrUtil.isEmpty(PADDING))) {
                    if (KEY.length() < MIN_KEY_LEN ) {
                        throw new IllegalArgumentException("key nnly support length 8");
                    }
                    des = new cn.hutool.crypto.symmetric.DES(KEY.getBytes());
                    if (StrUtil.isNotEmpty(IV)) {
                        des.setIv(IV.getBytes());
                    }
                } else if (StrUtil.isEmpty(KEY) && StrUtil.isNotEmpty(MODE) && StrUtil.isNotEmpty(PADDING)) {
                    Mode mode = Mode.valueOf(MODE);

                    Padding padding = Padding.valueOf(PADDING);
                    des = new cn.hutool.crypto.symmetric.DES(mode, padding);
                    if (StrUtil.isNotEmpty(IV)) {
                        des.setIv(IV.getBytes());
                    }
                } else {
                    des = new cn.hutool.crypto.symmetric.DES();
                    if (StrUtil.isNotEmpty(IV)) {
                        des.setIv(IV.getBytes());
                    }
                }
                DO_DES = des;
            }

        }
        return DO_DES;
    }

    public static cn.hutool.crypto.symmetric.DES getDes(){
        return doDes();
    }

    public static String encryptBase64(String data){
        return doDes().encryptBase64(data);
    }

    public static String decryptStrFromBase64(String data){
        return doDes().decryptStr(data);
    }

}
