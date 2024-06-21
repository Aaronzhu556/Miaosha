package com.example.miaosha.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    private final long EXPIRE_TIME=1800;
    /**
     * 给一个key附加过期时间
     * 如果time=0，默认设置为30分钟
     * @param: key time
     * @return:
     * */
    public boolean setExpireTime(String key,long time){
        return time==0?redisTemplate.expire(key,EXPIRE_TIME, TimeUnit.SECONDS): redisTemplate.expire(key,time, TimeUnit.SECONDS);
    }
    /**
     * 获取key的过期时间
     * @param: key
     * @return: time
     *
     * */
    public long getExpireTime(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }
    /**
     * 有没有这个key
     * @param: key
     * @return: boolean
     * */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    //------------------String-----------------//

    /**
     * 根据key获取值
     * @param: key
     * @return: value
     * */
    public Object getValueByKey(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 把值放入redis
     * @param: key value
     *
     * */
    public void  setValue(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }
    /**
     * 将值放入redis并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) -1为无期限
     * @return: true成功 false 失败
     */
    public void setValue(String key, String value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }
    //------------------List类型-----------------
    /**
     * 在变量左边添加元素值
     *
     * @param key
     * @param value
     * @return
     */
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合指定位置的值。
     *
     * @param key
     * @param index
     * @return
     */
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }


    /**
     * 向左边批量添加参数元素。
     *
     * @param key
     * @param values
     * @return
     */
    public void leftPushAll(String key, Object... values) {
//        redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向集合最右边添加元素。
     *
     * @param key
     * @param value
     * @return
     */
    public void rightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向右边批量添加参数元素。
     *
     * @param key
     * @param values
     * @return
     */
    public void rightPushAll(String key, Object... values) {
        //redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        redisTemplate.opsForList().rightPushAll(key, values);
        log.info("将数据增添进redis成功,key为{}",key);
    }


    // - - - - - - - - - - - - - - - - - - - - - hash类型 - - - - - - - - - - - - - -
    // - - - - - -

    /**
     * 加入缓存
     *
     * @param key 键
     * @param map 键  都是Integer类型
     * @return
     */
    public void add(String key, Map<Integer, Integer> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 加入缓存
     *
     * @param key 键
     * @param map 键
     * @return
     */
    public void addObject(String key, Map<Integer, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取 key 下的 所有 hashkey 和 value
     *
     * @param key 键
     * @return
     */
    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     *
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hasHashKey(String key, Integer hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取指定key的值value
     *   这个没看懂
     * @param key  键
     * @param hashKey 键==hashKey
     * @return
     */
    public Integer getMapValue(String key, Integer hashKey) {
        return (Integer) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key
     * @param hashKeys
     * @return 删除成功的 数量
     */
    public Long delete(String key, Integer... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key
     * @param hashKeys
     * @return 删除成功的 数量
     */
    public Long delete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key
     * @param hashKey
     * @param number
     * @return Integer
     */
    public Integer increment(String key, Integer hashKey, long number) {
        return Math.toIntExact(redisTemplate.opsForHash().increment(key, hashKey, number));
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key
     * @param hashKey
     * @param number
     * @return
     */
    public Double increment(String key, Integer hashKey, Double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 获取 key 下的 所有 hashkey 字段
     *
     * @param key
     * @return
     */
    public Set<Object> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取指定 hash 下面的 键值对 数量
     *
     * @param key
     * @return
     */
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }


    //  给某个 key 的hash里的 hashkey赋值

    public void hashSet(String key, Integer hashKey, Integer hashValue){
         redisTemplate.opsForHash().put(key,hashKey,hashValue);
    }


}
