package com.abapi.cloud.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * redission 锁
 * @author Administrator
 */
public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedissonClient redissonClient;

    private boolean locked = false;

    private RLock lock;

    public void lock(String name) {

        redissonClient = RedissonUtil.getRedissonClient();

        lock = redissonClient.getLock(name);
        try {
            locked = lock.tryLock(20L, 20L, TimeUnit.SECONDS);
            if (locked) {
                logger.info("获取锁 [{}] 线程 [{}]", name, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            logger.error("捕获到 InterruptedException", e);
            Thread.currentThread().interrupt();
        }
    }

    public void unLock() {
        if (locked) {
            if (lock.getHoldCount() > 0) {
                String name = lock.getName();
                logger.info("解锁[{}] 线程[{}]", name, Thread.currentThread().getName());
                lock.unlock();
            } else {
                logger.warn("解锁 线程 [{}],当前线程已不再占有锁", Thread.currentThread().getName());
            }
        }
    }
    
}
