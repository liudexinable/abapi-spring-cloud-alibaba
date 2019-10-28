abapi-spring-cloud-alibaba [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
===================================

[English Docs](https://github.com/YeautyYE/netty-websocket-spring-boot-starter/blob/master/README.md)

### 简介
本项目未spring-cloud-alibaba整合脚手架,学习,尝鲜

### 要求
- jdk版本为1.8或1.8+


### 模块说明
> 所有的配置项都在这个注解的属性中

| 编号  | 模块 | 说明 
|---|---|---
|1|abapi-cloud-start/abapi-cloud-dubbo-start|基于dubbo的pom引入
|2|abapi-cloud-start/abapi-cloud-getway-start|getway的pom引入
|3|abapi-cloud-start/abapi-cloud-nacos-start|nacos的pom引入
|4|abapi-cloud-start/abapi-cloud-seata-start|seata的pom引入
|5|abapi-cloud-start/abapi-cloud-sentinel-start|sentinel的pom引入
|6|abapi-plug-util/abapi-common-util|常用的基础util模块
|7|abapi-plug-util/abapi-pay-start|支付实现模块只支持(支付宝,微信)部分接口实现
|8|abapi-plug-util/abapi-netty-socket-start|基于netty实现的tcp,websocket脚手架
|9|abapi-plug-util/abapi-elasticsearch-start|spring-data-elasticsearch包的依赖引入

### 通过application.properties进行配置
> 对注解中的`prefix`进行设置后，即可在`application.properties`中进行配置。如下：


