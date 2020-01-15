package com.abapi.cloud.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author ldx
 * @Date 2019/11/20 16:43
 * @Description
 * @Version 1.0.0
 */
@Data
@ConfigurationProperties(RedissonProperties.PREFIX)
public class RedissonProperties {

    public static final String PREFIX = "abapi.cloud.redisson";

    private Boolean enabled = false;

    private String host;

    private Integer port;

    private String password;

    private String clusterNodes;

}
