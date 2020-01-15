package com.abapi.cloud.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author liu
 */
@Component
public class RedissonUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(RedissonUtil.class);

	private static RedissonClient redissonClient;

	private static String host;

	private static int port;

	private static String auth;

	private static String clusterNodes;

	public static RedissonClient getRedissonClient() {
		return redissonClient;
	}

	private RedissonUtil() {
	}

	public static RedissonUtil getInstance() {
		return InstanceHolder.instance;
	}

	public static void initRedission(){
		init();
	}

	private static class InstanceHolder {
		private static final RedissonUtil instance = new RedissonUtil();
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		RedissonUtil.host = host;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		RedissonUtil.port = port;
	}

	public static String getAuth() {
		return auth;
	}

	public static void setAuth(String auth) {
		RedissonUtil.auth = auth;
	}

	public static String getClusterNodes() {
		return clusterNodes;
	}

	public static void setClusterNodes(String clusterNodes){
		RedissonUtil.clusterNodes = clusterNodes;
	}

	@PostConstruct
	private static void init() {
		logger.info("init redissonClient..........{}", redissonClient);

		Config config = new Config();
		String hostName = RedissonUtil.host;
		String password = RedissonUtil.auth;
		int port = RedissonUtil.port;
		String clusterNodes = RedissonUtil.clusterNodes;
		logger.info("load hostInfo ... hostName="+hostName+" password="+password+" port="+port);
		final String comma = ",";
		if(!StringUtils.isEmpty(clusterNodes)){
			// 集群模式
			String [] l = clusterNodes.split(",");
			String [] clusterHost = new String[l.length];
			for (int i = 0; i < l.length; i++) {
				clusterHost[i] = "redis://"+l[i];
			}
			logger.info("集群 host"+clusterHost.toString());
			if(StringUtils.isEmpty(password)){
				config.useClusterServers()
						.setScanInterval(2000)
						.addNodeAddress(clusterHost);
			}else{
				config.useClusterServers()
						.setScanInterval(2000)
						.addNodeAddress(clusterHost)
						.setPassword(password);
			}
		}else{
			// 单机模式
			config.useSingleServer().setAddress("redis://" + hostName + ":" + port);
			if (!StringUtils.isEmpty(password)) {
				config.useSingleServer().setPassword(password);
			}
		}
		
		redissonClient = Redisson.create(config);

		logger.info("init redissonClient finish ..........{}", redissonClient);

	}
	
}
