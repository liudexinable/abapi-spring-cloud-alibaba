abapi-spring-cloud-alibaba [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
===================================

### 简介
本项目为spring-cloud-alibaba整合

分为框架整合和常用插件模块

abapi-cloud-start是spring cloud alibaba 微服务架构组件

 -abapi-cloud-web-start是springboot启动框架,引入改jar包项目就是一个标准的springboot项目
 
 -abapi-cloud-getway-start是微服务网关入口
 
 -abapi-cloud-nacos-start是服务发现/配置中心 [服务发现可替换zk等]
 
 -abapi-cloud-dubbo-start是rpc
 
 -abapi-cloud-seata-start是分布式事务,改项目未升级版本,请自行升级为最新的1.0.0.GA版本
 
 -abapi-cloud-sentinel-start是服务熔断,限流
 
abapi-plug-util是插件项目
### 要求
- jdk版本为1.8或1.8+


### 模块说明
> 所有的模块说明

| 编号  | 模块 | 说明 
|---|---|---
|1|abapi-cloud-start/abapi-cloud-dubbo-start|基于dubbo的pom引入
|2|abapi-cloud-start/abapi-cloud-getway-start|getway的pom引入
|3|abapi-cloud-start/abapi-cloud-nacos-start|nacos的pom引入
|4|abapi-cloud-start/abapi-cloud-seata-start|seata的pom引入
|5|abapi-cloud-start/abapi-cloud-sentinel-start|sentinel的pom引入
|6|abapi-cloud-start/abapi-cloud-web-start|springboot启动脚手架
|7|abapi-plug-util/abapi-common-util|常用的基础util模块
|8|abapi-plug-util/abapi-pay-start|支付实现模块只支持(支付宝,微信)部分接口实现
|9|abapi-plug-util/abapi-netty-socket-start|基于netty实现的tcp,websocket脚手架
|10|abapi-plug-util/abapi-elasticsearch-start|spring-data-elasticsearch包的依赖引入

### abapi-cloud-web-start 通过application.properties进行配置
### abapi-pay-start 快速开始
- 添加依赖:

```xml
<dependency>
	<groupId>com.abapi.cloud.plug</groupId>
	<artifactId>abapi-pay-start</artifactId>
	<version>1.0.0</version>
</dependency>
```

- 通过application.properties进行配置

| 属性  | 说明
|---|---
|1|支付宝
|abapi.cloud.pay.ali-app-id|支付宝商户id
|abapi.cloud.pay.ali-public-key|自己生成的RSA私钥
|abapi.cloud.pay.ali-private-key|自己生成的RSA公钥
|abapi.cloud.pay.ali-platform-public-key|支付宝平台返回的公钥
|abapi.cloud.pay.ali-sign-type|支付宝加密方式只支持(RSA2,RSA),默认RSA2
|abapi.cloud.pay.ali-public-key256|自己生成的RSA公钥256转码
|abapi.cloud.pay.ali-sandbox|是否开启沙箱环境,默认false(不开启)
|2|微信
|abapi.cloud.pay.wx-properties.trade-type|微信签约类型{APP:app,JSAPI:公众号、小程序,NATIVE:web,MWEB}
|abapi.cloud.pay.wx-properties.wx-app-id|微信appid
|abapi.cloud.pay.wx-properties.wx-ip|调用ip
|abapi.cloud.pay.wx-properties.wx-mch-id|商户id
|abapi.cloud.pay.wx-properties.wx-cert-path12|p12证书地址
|abapi.cloud.pay.wx-properties.wx-secret|signKey 加密对于的key
|abapi.cloud.pay.wx-properties.wx-sandbox|是否开启沙箱环境,默认false(不开启)
### abapi-netty-socket-start 快速开始
- 添加依赖:

```xml
<dependency>
	<groupId>com.abapi.cloud.plug</groupId>
	<artifactId>abapi-netty-socket-start</artifactId>
	<version>1.0.0</version>
</dependency>
```
	
- 通过application.properties进行配置

| 属性  |默认值| 说明
|---|---|---
|1|tpc
|abapi.cloud.netty.tcp.enabled|false|开启tcp
abapi.cloud.netty.tcp.host|0.0.0.0|监听ip
abapi.cloud.netty.tcp.port|9979|端口号
abapi.cloud.netty.tcp.reader-idle-time|0|与IdleStateHandler中的readerIdleTimeSeconds一致
abapi.cloud.netty.tcp.writer-idle-time|0|与IdleStateHandler中的writerIdleTimeSeconds一致
abapi.cloud.netty.tcp.all-idle-time|0|与IdleStateHandler中的allIdleTimeSeconds一致
abapi.cloud.netty.tcp.decoder||解码字符
abapi.cloud.netty.tcp.custom-message-decoder||自己实现的解码器(完整类名)
abapi.cloud.netty.tcp.custom-message-encoder||自己实现的编码器(完整类名)
|2|websocket




