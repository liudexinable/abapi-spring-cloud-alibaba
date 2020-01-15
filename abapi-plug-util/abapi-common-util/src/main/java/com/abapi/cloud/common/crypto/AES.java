package com.abapi.cloud.common.crypto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;

/**
 * @Author ldx
 * @Date 2019/9/24 11:13
 * @Description
 * @Version 1.0.0
 */
public class AES {

    public static String KEY;

    public static String IV;

    public static String MODE;

    public static String PADDING;

    private static Integer MIN_KEY_LEN = 16;

    private static Integer CEN_KEY_LEN = 24;

    private static Integer MAX_KEY_LEN = 32;

    private static volatile cn.hutool.crypto.symmetric.AES DO_AES;

    private static cn.hutool.crypto.symmetric.AES doAes () {
        if(DO_AES == null){
            synchronized (AES.class){
                cn.hutool.crypto.symmetric.AES aes;
                if(StrUtil.isNotEmpty(KEY) &&
                    StrUtil.isNotEmpty(MODE) && StrUtil.isNotEmpty(PADDING)){

                    if(KEY.length() != MIN_KEY_LEN && KEY.length() != CEN_KEY_LEN && KEY.length() != MAX_KEY_LEN){
                        throw new IllegalArgumentException("key only support [128-16,192-24,256-32]");
                    }

                    Mode mode = Mode.valueOf(MODE);

                    Padding padding = Padding.valueOf(PADDING);

                    aes = new cn.hutool.crypto.symmetric.AES(mode,padding,KEY.getBytes());
                    if(StrUtil.isNotEmpty(IV)){
                        aes.setIv(IV.getBytes());
                    }
                }else if(StrUtil.isNotEmpty(KEY) &&
                        (StrUtil.isEmpty(MODE) || StrUtil.isEmpty(PADDING))){
                    if(KEY.length() != MIN_KEY_LEN && KEY.length() != CEN_KEY_LEN && KEY.length() != MAX_KEY_LEN){
                        throw new IllegalArgumentException("key nnly support [128-16,192-24,256-32]");
                    }
                    aes = new cn.hutool.crypto.symmetric.AES(KEY.getBytes());
                    if(StrUtil.isNotEmpty(IV)){
                        aes.setIv(IV.getBytes());
                    }
                }else if(StrUtil.isEmpty(KEY) &&  StrUtil.isNotEmpty(MODE) && StrUtil.isNotEmpty(PADDING)){
                    Mode mode = Mode.valueOf(MODE);

                    Padding padding = Padding.valueOf(PADDING);

                    aes = new cn.hutool.crypto.symmetric.AES(mode,padding);
                    if(StrUtil.isNotEmpty(IV)){
                        aes.setIv(IV.getBytes());
                    }
                }else{
                    aes = new cn.hutool.crypto.symmetric.AES();
                    if(StrUtil.isNotEmpty(IV)){
                        aes.setIv(IV.getBytes());
                    }
                }
                DO_AES = aes;
            }
        }
        return DO_AES;
    }

    public static cn.hutool.crypto.symmetric.AES getAes(){
        return doAes();
    }

    public static String encryptBase64(String data){
        return doAes().encryptBase64(data);
    }

    public static String decryptStrFromBase64(String data){
        return doAes().decryptStr(data);
    }

}
