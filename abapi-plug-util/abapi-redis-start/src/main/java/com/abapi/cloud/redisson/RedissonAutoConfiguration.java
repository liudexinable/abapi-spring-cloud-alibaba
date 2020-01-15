package com.abapi.cloud.redisson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis 配置
 * @author liu
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(name = "abapi.cloud.redisson.lock.enabled", matchIfMissing = true)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonUtil redissonUtil(){
        if(!redissonProperties.getEnabled()){
            return null;
        }
        RedissonUtil.setHost(redissonProperties.getHost());
        RedissonUtil.setPort(redissonProperties.getPort());
        RedissonUtil.setAuth(redissonProperties.getPassword());
        RedissonUtil.setClusterNodes(redissonProperties.getClusterNodes());
        RedissonUtil instance = RedissonUtil.getInstance();
        RedissonUtil.initRedission();
        return instance;
    }

}
