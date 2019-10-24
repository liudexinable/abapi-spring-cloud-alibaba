package com.abapi.cloud.web.auto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @Author ldx
 * @Date 2019/9/27 18:11
 * @Description
 * @Version 1.0.0
 */
@Data
@ConfigurationProperties(AbapiWebConfigProperties.PREFIX)
public class AbapiWebConfigProperties {

    public static final String PREFIX = "abapi.cloud.web.config";

    private Long snowflakeWorkerid = 1L;

    private Long snowflakeDatacenterid = 1L;

    private String aesKey;

    private String aesIv;

    private String aesMode;

    private String aesPadding;

    private String desKey;

    private String desIv;

    private String desMode;

    private String desPadding;
}
