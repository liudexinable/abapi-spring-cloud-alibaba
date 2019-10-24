package com.abapi.cloud.web.auto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import com.abapi.cloud.common.crypto.AES;
import com.abapi.cloud.common.crypto.DES;
import com.abapi.cloud.common.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author ldx
 * @Date 2019/9/29 10:32
 * @Description
 * @Version 1.0.0
 */
@Configuration
@EnableConfigurationProperties({AbapiWebConfigProperties.class})
@ConditionalOnProperty(name = "abapi.cloud.web.config.enabled", matchIfMissing = true)
public class AbapiWebConfigAutoConfiguration {

    @Autowired
    AbapiWebConfigProperties abapiWebConfigProperties;

    @Bean
    public Boolean initAbapiWebConfig(){
        loadSnowflake();
        loadAes();
        loadDes();
        return true;
    }

    public void loadSnowflake(){
        IdUtil.WORKERID = abapiWebConfigProperties.getSnowflakeWorkerid();
        IdUtil.DATACENTERID = abapiWebConfigProperties.getSnowflakeDatacenterid();
    }

    private void loadAes(){
        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getAesKey())){
            AES.KEY = abapiWebConfigProperties.getAesKey();
        }

        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getAesIv())){
            AES.IV = abapiWebConfigProperties.getAesIv();
        }

        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getAesMode())){
            Optional<Mode> first = Arrays.asList(Mode.values()).stream().filter(e -> e.toString().equals(abapiWebConfigProperties.getAesMode())).findFirst();
            if(first.isPresent()){
                AES.MODE = abapiWebConfigProperties.getAesMode();
            }
        }

        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getAesPadding())){
            Optional<Padding> first = Arrays.asList(Padding.values()).stream().filter(e -> e.toString().equals(abapiWebConfigProperties.getAesPadding())).findFirst();
            if(first.isPresent()){
                AES.PADDING = abapiWebConfigProperties.getAesPadding();
            }
        }
    }

    private void loadDes(){
        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getDesKey())){
            DES.KEY = abapiWebConfigProperties.getDesKey();
        }

        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getDesIv())){
            DES.IV = abapiWebConfigProperties.getDesIv();
        }

        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getDesMode())){
            Optional<Mode> first = Arrays.asList(Mode.values()).stream().filter(e -> e.toString().equals(abapiWebConfigProperties.getDesMode())).findFirst();
            if(first.isPresent()){
                DES.MODE = abapiWebConfigProperties.getDesMode();
            }
        }

        if(StrUtil.isNotEmpty(abapiWebConfigProperties.getDesPadding())){
            Optional<Padding> first = Arrays.asList(Padding.values()).stream().filter(e -> e.toString().equals(abapiWebConfigProperties.getDesPadding())).findFirst();
            if(first.isPresent()){
                DES.PADDING = abapiWebConfigProperties.getDesPadding();
            }
        }
    }

}
