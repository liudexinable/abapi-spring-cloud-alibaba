package com.abapi.cloud.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author ldx
 * @Date 2019/11/20 15:55
 * @Description
 * @Version 1.0.0
 */
@Component
public class SRedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static SRedisUtil sRedisUtil;

    @PostConstruct
    public void init(){
        sRedisUtil = this;
        sRedisUtil.redisTemplate = redisTemplate;
    }

    public static RedisTemplate getRedisTemplate(){
        return sRedisUtil.redisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                sRedisUtil.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return sRedisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return sRedisUtil.redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                sRedisUtil.redisTemplate.delete(key[0]);
            } else {
                sRedisUtil.redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }



    /**
     * 删除对应的value
     *
     * @param key
     */
    public static void remove(final String key) {
        if (exists(key)) {
            sRedisUtil.redisTemplate.delete(key);
        }
    }

    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        return sRedisUtil.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return sRedisUtil.redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            sRedisUtil.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            sRedisUtil.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            sRedisUtil.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            sRedisUtil.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        sRedisUtil.redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return sRedisUtil.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        return sRedisUtil.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        return sRedisUtil.redisTemplate.opsForHash().increment(key, item, -by);
    }




    //**********************************list***************************************//

    /**
     * 向List头部追加记录
     * @param key
     * @param value
     * @return 记录总数
     * */
    public static boolean leftPush(final String key, Object value) {
        boolean result = false;
        try {
            ListOperations<Serializable, Object> operations = sRedisUtil.redisTemplate.opsForList();
            operations.leftPush(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * List长度
     * @param key
     * @return 长度
     * */
    public static long size(byte[] key) {
        return sRedisUtil.redisTemplate.opsForList().size(key);
    }

    /**
     * 获取List中指定位置的值
     * @param key
     * @param index 位置
     * @return 值
     * **/
    public static Object index(byte[] key, long index) {
        return sRedisUtil.redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将List中的第一条记录移出List
     * @param key
     * @return 移出的记录
     * */
    public static Object leftPop(byte[] key) {
        return sRedisUtil.redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 将List中的最后一条记录移出List
     * @param key
     * @return 移出的记录
     * */
    public static Object rightPop(byte[] key) {
        return sRedisUtil.redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 向List尾部追加记录
     * @param key
     * @param value
     * @return 记录总数
     * */
    public static Long rpush(String key, String value) {
        return sRedisUtil.redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> range(String key, long start, long end) {
        try {
            return sRedisUtil.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return sRedisUtil.redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, Object value) {
        try {
            sRedisUtil.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            sRedisUtil.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            sRedisUtil.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            sRedisUtil.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            sRedisUtil.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = sRedisUtil.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //**********************************Set***************************************//

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return sRedisUtil.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return sRedisUtil.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return sRedisUtil.redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = sRedisUtil.redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        try {
            return sRedisUtil.redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            Long count = sRedisUtil.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



    /**
     * 批量删除key
     *
     * @param pattern
     */
    public static void removePattern(final String pattern) {
        Set<Serializable> keys = sRedisUtil.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            sRedisUtil.redisTemplate.delete(keys);
        }
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return sRedisUtil.redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = sRedisUtil.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = sRedisUtil.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = sRedisUtil.redisTemplate.opsForValue();
            operations.set(key, value);
            sRedisUtil.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断缓存中是否 hash
     * @param key
     * @return
     */
    public static boolean existsHash(final String key,final Object hashKey) {
        HashOperations<String,String,Object> operations = sRedisUtil.redisTemplate.opsForHash();
        return operations.hasKey(key,hashKey);
    }

    /**
     * 写入 hash
     * @param key 主key
     * @param hashKey hashKey
     * @param value hashValue
     * @return
     */
    public static boolean setHash(final String key,final Object hashKey,Object value){
        boolean result = false;
        try {
            HashOperations<String,Object,Object> operations = sRedisUtil.redisTemplate.opsForHash();
            operations.put(key,hashKey,value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入 hash 并设置设计
     * @param key 主key
     * @param hashKey hashKey
     * @param value hashValue
     * @param expireTime 过期时间 秒
     * @return
     */
    public static boolean setHash(final String key,final Object hashKey,Object value,Long expireTime){
        boolean result = false;
        try {
            HashOperations<String,Object,Object> operations = sRedisUtil.redisTemplate.opsForHash();
            operations.put(key,hashKey,value);
            sRedisUtil.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取 全部hash 数据
     * @param key
     * @return
     */
    public static List<Object> getHashAll(final String key){
        List<Object> result = null;
        try {
            HashOperations<String,Object,Object> operations = sRedisUtil.redisTemplate.opsForHash();
            result = operations.values(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取 指定 hashKey 数据
     * @param key 主key
     * @param hashKey hashKey
     * @return
     */
    public static Object getHashByHashKey(final String key,final Object hashKey){
        Object result = null;
        try {
            HashOperations<String,Object,Object> operations = sRedisUtil.redisTemplate.opsForHash();
            result = operations.get(key,hashKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除 hash key
     * @param key
     * @param hashKey
     * @return
     */
    public static boolean delHashKey(final  String key,final Object... hashKey){
        boolean result = false;
        try {
            HashOperations<String,Object,Object> operations = sRedisUtil.redisTemplate.opsForHash();
            operations.delete(key,hashKey);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Set<String> keys(String key) {
        return sRedisUtil.redisTemplate.keys(key);
    }

}
